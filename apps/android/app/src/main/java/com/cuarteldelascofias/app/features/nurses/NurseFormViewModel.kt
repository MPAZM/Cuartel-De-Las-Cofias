package com.cuarteldelascofias.app.features.nurses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.core.common.AppCatalogs
import com.cuarteldelascofias.app.data.repository.NurseDraft
import com.cuarteldelascofias.app.data.repository.NurseRepository

private val nurseNameRegex = Regex("^[\\p{L} .,'-]+$")
private val nurseShortTextRegex = Regex("^[\\p{L}0-9 .,'()/-]+$")

enum class NurseField {
    FULL_NAME,
    PHONE,
    BASE_ZONE,
    AVAILABILITY_LABEL,
    TRUST_LEVEL
}

data class NurseValidationError(
    val field: NurseField,
    val message: String
)

data class NurseFormUiState(
    val fullName: String = "",
    val phone: String = "",
    val specialtyNotes: String = "",
    val baseZone: String = "",
    val availabilityLabel: String = "",
    val trustLevel: String = "",
    val notes: String = "",
    val active: Boolean = true,
    val validationErrors: List<NurseValidationError> = emptyList()
) {
    fun hasError(field: NurseField): Boolean {
        return validationErrors.any { it.field == field }
    }
}

class NurseFormViewModel(
    private val nurseRepository: NurseRepository
) : ViewModel() {
    var uiState by mutableStateOf(NurseFormUiState())
        private set

    fun onFullNameChange(value: String) {
        uiState = uiState.copy(fullName = value, validationErrors = emptyList())
    }

    fun onPhoneChange(value: String) {
        val sanitizedValue = value.filter { it.isDigit() || it in " +()-" }.take(20)
        uiState = uiState.copy(phone = sanitizedValue, validationErrors = emptyList())
    }

    fun onSpecialtyNotesChange(value: String) {
        uiState = uiState.copy(specialtyNotes = value, validationErrors = emptyList())
    }

    fun onBaseZoneChange(value: String) {
        uiState = uiState.copy(baseZone = value, validationErrors = emptyList())
    }

    fun onAvailabilityLabelChange(value: String) {
        uiState = uiState.copy(availabilityLabel = value, validationErrors = emptyList())
    }

    fun onTrustLevelChange(value: String) {
        uiState = uiState.copy(trustLevel = value, validationErrors = emptyList())
    }

    fun onNotesChange(value: String) {
        uiState = uiState.copy(notes = value, validationErrors = emptyList())
    }

    fun onActiveChange(value: Boolean) {
        uiState = uiState.copy(active = value, validationErrors = emptyList())
    }

    fun saveNurse(): Boolean {
        val errors = validate(uiState)
        if (errors.isNotEmpty()) {
            uiState = uiState.copy(validationErrors = errors)
            return false
        }

        nurseRepository.addNurse(
            NurseDraft(
                fullName = uiState.fullName,
                phone = uiState.phone,
                specialtyNotes = uiState.specialtyNotes,
                baseZone = uiState.baseZone,
                availabilityLabel = uiState.availabilityLabel,
                active = uiState.active,
                trustLevel = uiState.trustLevel,
                notes = uiState.notes
            )
        )
        return true
    }

    private fun validate(state: NurseFormUiState): List<NurseValidationError> {
        val errors = mutableListOf<NurseValidationError>()

        if (state.fullName.isBlank()) {
            errors += NurseValidationError(NurseField.FULL_NAME, "Falta el nombre completo de la enfermera.")
        } else if (!nurseNameRegex.matches(state.fullName.trim())) {
            errors += NurseValidationError(NurseField.FULL_NAME, "El nombre de la enfermera contiene caracteres no esperados.")
        }

        val phoneDigits = state.phone.filter { it.isDigit() }
        if (state.phone.isBlank()) {
            errors += NurseValidationError(NurseField.PHONE, "Falta el telefono principal.")
        } else if (phoneDigits.length !in 8..15) {
            errors += NurseValidationError(NurseField.PHONE, "El telefono debe tener entre 8 y 15 digitos.")
        }

        if (state.baseZone.isBlank()) {
            errors += NurseValidationError(NurseField.BASE_ZONE, "Falta la zona base.")
        } else if (!nurseShortTextRegex.matches(state.baseZone.trim())) {
            errors += NurseValidationError(NurseField.BASE_ZONE, "La zona base contiene caracteres no esperados.")
        }

        if (state.availabilityLabel.isBlank()) {
            errors += NurseValidationError(NurseField.AVAILABILITY_LABEL, "Falta la disponibilidad operativa.")
        } else if (AppCatalogs.nurseAvailability.none { it.label == state.availabilityLabel }) {
            errors += NurseValidationError(NurseField.AVAILABILITY_LABEL, "Selecciona una disponibilidad valida.")
        }

        if (state.trustLevel.isNotBlank() && AppCatalogs.nurseTrustLevels.none { it.label == state.trustLevel }) {
            errors += NurseValidationError(NurseField.TRUST_LEVEL, "Selecciona un nivel de confianza valido.")
        }

        return errors
    }

    companion object {
        fun factory(nurseRepository: NurseRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    NurseFormViewModel(nurseRepository)
                }
            }
    }
}
