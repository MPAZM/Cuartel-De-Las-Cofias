package com.cuarteldelascofias.app.features.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.core.navigation.AppDestination
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.model.Shift
import com.cuarteldelascofias.app.data.model.ShiftReport
import com.cuarteldelascofias.app.data.repository.PatientRepository
import com.cuarteldelascofias.app.data.repository.ShiftReportDraft
import com.cuarteldelascofias.app.data.repository.ShiftReportRepository
import com.cuarteldelascofias.app.data.repository.ShiftRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

private val reportAllowedCharsRegex = Regex("^[\\p{L}0-9 .,;:()/%+\\-\\n\\r]+$")

enum class ShiftReportField {
    GENERAL_STATUS,
    MEDICATION_NOTES,
    VITAL_SIGNS_NOTES,
    RELEVANT_EVENTS,
    HOSPITAL_STAFF_COMMENTS,
    FAMILY_COMMENTS,
    HANDOFF_SUMMARY
}

data class ShiftReportValidationError(
    val field: ShiftReportField,
    val message: String
)

data class ShiftReportUiState(
    val shift: Shift? = null,
    val patient: Patient? = null,
    val report: ShiftReport? = null,
    val generalStatus: String = "",
    val medicationNotes: String = "",
    val vitalSignsNotes: String = "",
    val relevantEvents: String = "",
    val hospitalStaffComments: String = "",
    val familyComments: String = "",
    val handoffSummary: String = "",
    val validationErrors: List<ShiftReportValidationError> = emptyList(),
    val saveSucceeded: Boolean = false
) {
    fun hasError(field: ShiftReportField): Boolean {
        return validationErrors.any { it.field == field }
    }
}

class ShiftReportViewModel(
    savedStateHandle: SavedStateHandle,
    shiftRepository: ShiftRepository,
    patientRepository: PatientRepository,
    private val shiftReportRepository: ShiftReportRepository
) : ViewModel() {
    private val shiftId: String =
        checkNotNull(savedStateHandle[AppDestination.ShiftReport.shiftIdArg])

    var uiState by mutableStateOf(ShiftReportUiState())
        private set

    init {
        viewModelScope.launch {
            combine(
                shiftRepository.shifts,
                patientRepository.patients,
                shiftReportRepository.reports
            ) { shifts, patients, reports ->
                val shift = shifts.firstOrNull { it.id == shiftId }
                val patient = shift?.let { currentShift ->
                    patients.firstOrNull { it.id == currentShift.patientId }
                }
                val report = reports.firstOrNull { it.shiftId == shiftId }

                ShiftReportUiState(
                    shift = shift,
                    patient = patient,
                    report = report,
                    generalStatus = report?.generalStatus ?: uiState.generalStatus,
                    medicationNotes = report?.medicationNotes ?: uiState.medicationNotes,
                    vitalSignsNotes = report?.vitalSignsNotes ?: uiState.vitalSignsNotes,
                    relevantEvents = report?.relevantEvents ?: uiState.relevantEvents,
                    hospitalStaffComments = report?.hospitalStaffComments ?: uiState.hospitalStaffComments,
                    familyComments = report?.familyComments ?: uiState.familyComments,
                    handoffSummary = report?.handoffSummary ?: uiState.handoffSummary,
                    validationErrors = uiState.validationErrors,
                    saveSucceeded = uiState.saveSucceeded
                )
            }.collect { state ->
                uiState = state
            }
        }
    }

    fun onGeneralStatusChange(value: String) {
        uiState = uiState.copy(generalStatus = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun onMedicationNotesChange(value: String) {
        uiState = uiState.copy(medicationNotes = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun onVitalSignsNotesChange(value: String) {
        uiState = uiState.copy(vitalSignsNotes = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun onRelevantEventsChange(value: String) {
        uiState = uiState.copy(relevantEvents = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun onHospitalStaffCommentsChange(value: String) {
        uiState = uiState.copy(hospitalStaffComments = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun onFamilyCommentsChange(value: String) {
        uiState = uiState.copy(familyComments = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun onHandoffSummaryChange(value: String) {
        uiState = uiState.copy(handoffSummary = value, validationErrors = emptyList(), saveSucceeded = false)
    }

    fun saveReport(): Boolean {
        val errors = validate(uiState)

        if (errors.isNotEmpty()) {
            uiState = uiState.copy(validationErrors = errors, saveSucceeded = false)
            return false
        }

        shiftReportRepository.upsertReport(
            ShiftReportDraft(
                shiftId = shiftId,
                generalStatus = uiState.generalStatus,
                medicationNotes = uiState.medicationNotes,
                vitalSignsNotes = uiState.vitalSignsNotes,
                relevantEvents = uiState.relevantEvents,
                hospitalStaffComments = uiState.hospitalStaffComments,
                familyComments = uiState.familyComments,
                handoffSummary = uiState.handoffSummary
            )
        )

        uiState = uiState.copy(validationErrors = emptyList(), saveSucceeded = true)
        return true
    }

    private fun validate(state: ShiftReportUiState): List<ShiftReportValidationError> {
        val errors = mutableListOf<ShiftReportValidationError>()

        validateRequiredNarrative(
            value = state.generalStatus,
            field = ShiftReportField.GENERAL_STATUS,
            emptyMessage = "Falta el estado general del paciente.",
            shortMessage = "El estado general debe explicar brevemente como se encuentra el paciente.",
            invalidMessage = "El estado general contiene caracteres no esperados.",
            minLength = 12,
            errors = errors
        )

        validateOptionalNarrative(
            value = state.medicationNotes,
            field = ShiftReportField.MEDICATION_NOTES,
            shortMessage = "Si capturas medicamentos y cuidados, agrega un dato mas claro o deja el campo vacio.",
            invalidMessage = "Medicamentos y cuidados contiene caracteres no esperados.",
            errors = errors
        )

        validateOptionalNarrative(
            value = state.vitalSignsNotes,
            field = ShiftReportField.VITAL_SIGNS_NOTES,
            shortMessage = "Si capturas signos vitales, agrega un dato mas claro o deja el campo vacio.",
            invalidMessage = "Signos vitales contiene caracteres no esperados.",
            errors = errors
        )

        validateOptionalNarrative(
            value = state.relevantEvents,
            field = ShiftReportField.RELEVANT_EVENTS,
            shortMessage = "Si capturas eventos relevantes, agrega un dato mas claro o deja el campo vacio.",
            invalidMessage = "Eventos relevantes contiene caracteres no esperados.",
            errors = errors
        )

        validateOptionalNarrative(
            value = state.hospitalStaffComments,
            field = ShiftReportField.HOSPITAL_STAFF_COMMENTS,
            shortMessage = "Si capturas comentarios del hospital, agrega un dato mas claro o deja el campo vacio.",
            invalidMessage = "Comentarios del hospital contiene caracteres no esperados.",
            errors = errors
        )

        validateOptionalNarrative(
            value = state.familyComments,
            field = ShiftReportField.FAMILY_COMMENTS,
            shortMessage = "Si capturas comentarios para la familia, agrega un dato mas claro o deja el campo vacio.",
            invalidMessage = "Comentarios para la familia contiene caracteres no esperados.",
            errors = errors
        )

        validateRequiredNarrative(
            value = state.handoffSummary,
            field = ShiftReportField.HANDOFF_SUMMARY,
            emptyMessage = "Falta el resumen de entrega de turno.",
            shortMessage = "La entrega de turno debe resumir lo pendiente para la siguiente persona.",
            invalidMessage = "La entrega de turno contiene caracteres no esperados.",
            minLength = 16,
            errors = errors
        )

        return errors
    }

    private fun validateRequiredNarrative(
        value: String,
        field: ShiftReportField,
        emptyMessage: String,
        shortMessage: String,
        invalidMessage: String,
        minLength: Int,
        errors: MutableList<ShiftReportValidationError>
    ) {
        val normalized = value.normalizedNarrative()
        when {
            normalized.isEmpty() -> errors += ShiftReportValidationError(field, emptyMessage)
            normalized.length < minLength -> errors += ShiftReportValidationError(field, shortMessage)
            !reportAllowedCharsRegex.matches(value) -> errors += ShiftReportValidationError(field, invalidMessage)
        }
    }

    private fun validateOptionalNarrative(
        value: String,
        field: ShiftReportField,
        shortMessage: String,
        invalidMessage: String,
        errors: MutableList<ShiftReportValidationError>
    ) {
        val normalized = value.normalizedNarrative()
        if (normalized.isEmpty()) return

        when {
            normalized.length < 4 -> errors += ShiftReportValidationError(field, shortMessage)
            !reportAllowedCharsRegex.matches(value) -> errors += ShiftReportValidationError(field, invalidMessage)
        }
    }

    private fun String.normalizedNarrative(): String = trim().replace(Regex("\\s+"), " ")

    companion object {
        fun factory(
            shiftRepository: ShiftRepository,
            patientRepository: PatientRepository,
            shiftReportRepository: ShiftReportRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShiftReportViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    shiftRepository = shiftRepository,
                    patientRepository = patientRepository,
                    shiftReportRepository = shiftReportRepository
                )
            }
        }
    }
}
