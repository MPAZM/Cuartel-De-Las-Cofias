package com.cuarteldelascofias.app.features.shifts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.core.common.AppCatalogs
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.model.ShiftStatus
import com.cuarteldelascofias.app.data.repository.NurseRepository
import com.cuarteldelascofias.app.data.repository.PatientRepository
import com.cuarteldelascofias.app.data.repository.ShiftDraft
import com.cuarteldelascofias.app.data.repository.ShiftRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ShiftField {
    PATIENT,
    SERVICE_TYPE,
    LOCATION,
    START,
    END,
    RATE,
    COMMISSION
}

data class ShiftValidationError(
    val field: ShiftField,
    val message: String
)

data class ShiftFormUiState(
    val patients: List<Patient> = emptyList(),
    val nurses: List<String> = emptyList(),
    val patientQuery: String = "",
    val nurseQuery: String = "",
    val selectedPatientId: String? = null,
    val selectedNurseName: String? = null,
    val serviceType: String = "",
    val locationSummary: String = "",
    val startDateTimeMillis: Long? = null,
    val endDateTimeMillis: Long? = null,
    val rateAmount: String = "",
    val commissionAmount: String = "",
    val adminNotes: String = "",
    val status: ShiftStatus = ShiftStatus.SCHEDULED,
    val validationErrors: List<ShiftValidationError> = emptyList()
) {
    fun hasError(field: ShiftField): Boolean {
        return validationErrors.any { it.field == field }
    }

    val filteredPatients: List<Patient>
        get() = if (patientQuery.isBlank()) {
            patients
        } else {
            patients.filter {
                it.displayName.contains(patientQuery, ignoreCase = true) ||
                    it.locationSummary.contains(patientQuery, ignoreCase = true)
            }
        }

    val filteredNurses: List<String>
        get() = if (nurseQuery.isBlank()) {
            nurses
        } else {
            nurses.filter { it.contains(nurseQuery, ignoreCase = true) }
        }

    val selectedPatientLabel: String
        get() = patients.firstOrNull { it.id == selectedPatientId }?.displayName.orEmpty()
}

class ShiftFormViewModel(
    patientRepository: PatientRepository,
    nurseRepository: NurseRepository,
    private val shiftRepository: ShiftRepository
) : ViewModel() {
    var uiState by mutableStateOf(ShiftFormUiState())
        private set

    init {
        viewModelScope.launch {
            combine(
                patientRepository.patients,
                nurseRepository.nurses
            ) { patients, nurses ->
                patients to nurses.map { it.fullName }
            }.collect { (patients, nurseNames) ->
                uiState = uiState.copy(
                    patients = patients,
                    nurses = nurseNames,
                    selectedPatientId = uiState.selectedPatientId?.takeIf { selectedId ->
                        patients.any { it.id == selectedId }
                    },
                    selectedNurseName = uiState.selectedNurseName?.takeIf { nurseNames.contains(it) }
                )
            }
        }
    }

    fun onPatientSelected(patientId: String) {
        val selectedPatient = uiState.patients.firstOrNull { it.id == patientId }
        uiState = uiState.copy(
            patientQuery = selectedPatient?.displayName.orEmpty(),
            selectedPatientId = patientId,
            locationSummary = selectedPatient?.locationSummary.orEmpty(),
            validationErrors = emptyList()
        )
    }

    fun onPatientQueryChange(value: String) {
        uiState = uiState.copy(
            patientQuery = value,
            selectedPatientId = uiState.selectedPatientId?.takeIf { selectedId ->
                uiState.patients.firstOrNull { it.id == selectedId }?.displayName == value
            },
            validationErrors = emptyList()
        )
    }

    fun onNurseSelected(nurseName: String?) {
        uiState = uiState.copy(
            nurseQuery = nurseName.orEmpty(),
            selectedNurseName = nurseName,
            validationErrors = emptyList()
        )
    }

    fun onNurseQueryChange(value: String) {
        uiState = uiState.copy(
            nurseQuery = value,
            selectedNurseName = uiState.selectedNurseName?.takeIf { it == value },
            validationErrors = emptyList()
        )
    }

    fun onServiceTypeChange(value: String) {
        uiState = uiState.copy(serviceType = value, validationErrors = emptyList())
    }

    fun onLocationSummaryChange(value: String) {
        uiState = uiState.copy(locationSummary = value, validationErrors = emptyList())
    }

    fun onStartDateTimeSelected(value: Long) {
        uiState = uiState.copy(startDateTimeMillis = value, validationErrors = emptyList())
    }

    fun onEndDateTimeSelected(value: Long) {
        uiState = uiState.copy(endDateTimeMillis = value, validationErrors = emptyList())
    }

    fun onRateAmountChange(value: String) {
        uiState = uiState.copy(rateAmount = value.filter { it.isDigit() }, validationErrors = emptyList())
    }

    fun onCommissionAmountChange(value: String) {
        uiState = uiState.copy(commissionAmount = value.filter { it.isDigit() }, validationErrors = emptyList())
    }

    fun onAdminNotesChange(value: String) {
        uiState = uiState.copy(adminNotes = value, validationErrors = emptyList())
    }

    fun onStatusSelected(status: ShiftStatus) {
        uiState = uiState.copy(status = status, validationErrors = emptyList())
    }

    fun formattedDateTime(value: Long?): String {
        if (value == null) return "Sin seleccionar"
        return dateTimeFormatter.format(Date(value))
    }

    fun saveShift(): Boolean {
        val errors = validate(uiState)
        if (errors.isNotEmpty()) {
            uiState = uiState.copy(validationErrors = errors)
            return false
        }

        val startMillis = checkNotNull(uiState.startDateTimeMillis)
        val endMillis = checkNotNull(uiState.endDateTimeMillis)

        shiftRepository.addShift(
            ShiftDraft(
                patientId = checkNotNull(uiState.selectedPatientId),
                nurseName = uiState.selectedNurseName,
                serviceType = uiState.serviceType,
                locationSummary = uiState.locationSummary,
                startLabel = formattedDateTime(startMillis),
                endLabel = formattedDateTime(endMillis),
                scheduleSummary = buildScheduleSummary(startMillis, endMillis),
                rateAmount = uiState.rateAmount.toIntOrNull() ?: 0,
                commissionAmount = uiState.commissionAmount.toIntOrNull() ?: 0,
                status = uiState.status,
                adminNotes = uiState.adminNotes
            )
        )
        return true
    }

    private fun validate(state: ShiftFormUiState): List<ShiftValidationError> {
        val errors = mutableListOf<ShiftValidationError>()

        if (state.selectedPatientId == null) {
            errors += ShiftValidationError(ShiftField.PATIENT, "Selecciona un paciente para la guardia.")
        }

        if (state.serviceType.isBlank()) {
            errors += ShiftValidationError(ShiftField.SERVICE_TYPE, "Falta el tipo de servicio.")
        } else if (AppCatalogs.shiftServiceTypes.none { it.label == state.serviceType }) {
            errors += ShiftValidationError(ShiftField.SERVICE_TYPE, "Selecciona un tipo de servicio valido.")
        }

        if (state.locationSummary.isBlank()) {
            errors += ShiftValidationError(ShiftField.LOCATION, "Falta confirmar la ubicacion de la guardia.")
        }

        if (state.startDateTimeMillis == null) {
            errors += ShiftValidationError(ShiftField.START, "Selecciona la fecha y hora de inicio.")
        }

        if (state.endDateTimeMillis == null) {
            errors += ShiftValidationError(ShiftField.END, "Selecciona la fecha y hora de fin.")
        }

        if (state.startDateTimeMillis != null && state.endDateTimeMillis != null &&
            state.endDateTimeMillis <= state.startDateTimeMillis
        ) {
            errors += ShiftValidationError(ShiftField.END, "La fecha y hora de fin debe ser posterior al inicio.")
        }

        val rate = state.rateAmount.toIntOrNull()
        if (state.rateAmount.isBlank()) {
            errors += ShiftValidationError(ShiftField.RATE, "Falta la tarifa total.")
        } else if (rate == null || rate <= 0) {
            errors += ShiftValidationError(ShiftField.RATE, "La tarifa debe ser un numero mayor a 0.")
        }

        val commission = state.commissionAmount.toIntOrNull() ?: 0
        if (state.commissionAmount.isNotBlank() && commission < 0) {
            errors += ShiftValidationError(ShiftField.COMMISSION, "La comision no puede ser negativa.")
        }
        if (rate != null && commission > rate) {
            errors += ShiftValidationError(ShiftField.COMMISSION, "La comision no puede ser mayor que la tarifa.")
        }

        return errors
    }

    private fun buildScheduleSummary(startMillis: Long, endMillis: Long): String {
        return "${shortDateTimeFormatter.format(Date(startMillis))} a ${shortDateTimeFormatter.format(Date(endMillis))}"
    }

    companion object {
        private val locale = Locale.forLanguageTag("es-MX")
        private val dateTimeFormatter = SimpleDateFormat("d MMM yyyy, h:mm a", locale)
        private val shortDateTimeFormatter = SimpleDateFormat("d MMM, h:mm a", locale)

        fun factory(
            patientRepository: PatientRepository,
            nurseRepository: NurseRepository,
            shiftRepository: ShiftRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShiftFormViewModel(
                    patientRepository = patientRepository,
                    nurseRepository = nurseRepository,
                    shiftRepository = shiftRepository
                )
            }
        }
    }
}
