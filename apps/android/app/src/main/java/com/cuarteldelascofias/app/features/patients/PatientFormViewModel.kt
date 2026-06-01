package com.cuarteldelascofias.app.features.patients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.core.common.AppCatalogs
import com.cuarteldelascofias.app.core.navigation.AppDestination
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.repository.PatientDraft
import com.cuarteldelascofias.app.data.repository.PatientRepository

private val personNameRegex = Regex("^[\\p{L} .,'-]+$")

enum class PatientField {
    FULL_NAME,
    AGE,
    SERVICE_LOCATION_TYPE,
    SERVICE_ADDRESS,
    EMERGENCY_CONTACT_NAME,
    EMERGENCY_CONTACT_PHONE
}

data class PatientValidationError(
    val field: PatientField,
    val message: String
)

data class PatientFormUiState(
    val patientId: String? = null,
    val isEditMode: Boolean = false,
    val recordNotFound: Boolean = false,
    val fullName: String = "",
    val preferredName: String = "",
    val age: String = "",
    val careContext: String = "",
    val serviceLocationType: String = "",
    val serviceAddress: String = "",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val notes: String = "",
    val active: Boolean = true,
    val validationErrors: List<PatientValidationError> = emptyList()
) {
    fun hasError(field: PatientField): Boolean {
        return validationErrors.any { it.field == field }
    }
}

class PatientFormViewModel(
    savedStateHandle: SavedStateHandle,
    private val patientRepository: PatientRepository
) : ViewModel() {
    private val patientId: String? = savedStateHandle[AppDestination.PatientEdit.patientIdArg]

    var uiState by mutableStateOf(
        PatientFormUiState(
            patientId = patientId,
            isEditMode = patientId != null
        )
    )
        private set

    init {
        if (patientId != null) {
            loadExistingPatient(patientId)
        }
    }

    fun onFullNameChange(value: String) {
        uiState = uiState.copy(fullName = value, validationErrors = emptyList())
    }

    fun onPreferredNameChange(value: String) {
        uiState = uiState.copy(preferredName = value, validationErrors = emptyList())
    }

    fun onAgeChange(value: String) {
        uiState = uiState.copy(age = value.filter { it.isDigit() }.take(3), validationErrors = emptyList())
    }

    fun onCareContextChange(value: String) {
        uiState = uiState.copy(careContext = value, validationErrors = emptyList())
    }

    fun onServiceLocationTypeChange(value: String) {
        uiState = uiState.copy(serviceLocationType = value, validationErrors = emptyList())
    }

    fun onServiceAddressChange(value: String) {
        uiState = uiState.copy(serviceAddress = value, validationErrors = emptyList())
    }

    fun onEmergencyContactNameChange(value: String) {
        uiState = uiState.copy(emergencyContactName = value, validationErrors = emptyList())
    }

    fun onEmergencyContactPhoneChange(value: String) {
        val sanitizedValue = value.filter { it.isDigit() || it in " +()-" }.take(20)
        uiState = uiState.copy(emergencyContactPhone = sanitizedValue, validationErrors = emptyList())
    }

    fun onNotesChange(value: String) {
        uiState = uiState.copy(notes = value, validationErrors = emptyList())
    }

    fun onActiveChange(value: Boolean) {
        uiState = uiState.copy(active = value, validationErrors = emptyList())
    }

    fun savePatient(): Boolean {
        if (uiState.recordNotFound) return false

        val errors = validate(uiState)
        if (errors.isNotEmpty()) {
            uiState = uiState.copy(validationErrors = errors)
            return false
        }

        val draft = PatientDraft(
            fullName = uiState.fullName,
            preferredName = uiState.preferredName,
            age = uiState.age.toIntOrNull(),
            careContext = uiState.careContext,
            serviceLocationType = uiState.serviceLocationType,
            serviceAddress = uiState.serviceAddress,
            emergencyContactName = uiState.emergencyContactName,
            emergencyContactPhone = uiState.emergencyContactPhone,
            active = uiState.active,
            notes = uiState.notes
        )

        if (uiState.isEditMode) {
            val updatedPatient = patientRepository.updatePatient(
                patientId = checkNotNull(uiState.patientId),
                draft = draft
            )
            return updatedPatient != null
        }

        patientRepository.addPatient(draft)
        return true
    }

    private fun loadExistingPatient(patientId: String) {
        val patient = patientRepository.getPatientById(patientId)
        if (patient == null) {
            uiState = uiState.copy(recordNotFound = true)
        } else {
            uiState = patient.toFormState(isEditMode = true)
        }
    }

    private fun validate(state: PatientFormUiState): List<PatientValidationError> {
        val errors = mutableListOf<PatientValidationError>()

        if (state.fullName.isBlank()) {
            errors += PatientValidationError(PatientField.FULL_NAME, "Falta el nombre completo del paciente.")
        } else if (!personNameRegex.matches(state.fullName.trim())) {
            errors += PatientValidationError(PatientField.FULL_NAME, "El nombre completo contiene caracteres no esperados.")
        }

        state.age.toIntOrNull()?.let { age ->
            if (age !in 0..120) {
                errors += PatientValidationError(PatientField.AGE, "La edad debe estar entre 0 y 120 anos.")
            }
        }
        if (state.age.isNotBlank() && state.age.toIntOrNull() == null) {
            errors += PatientValidationError(PatientField.AGE, "La edad debe ser numerica.")
        }

        if (state.serviceLocationType.isBlank()) {
            errors += PatientValidationError(PatientField.SERVICE_LOCATION_TYPE, "Falta el tipo de ubicacion del servicio.")
        } else if (AppCatalogs.patientLocationTypes.none { it.label == state.serviceLocationType }) {
            errors += PatientValidationError(PatientField.SERVICE_LOCATION_TYPE, "Selecciona un tipo de ubicacion valido.")
        }

        if (state.serviceAddress.isBlank()) {
            errors += PatientValidationError(PatientField.SERVICE_ADDRESS, "Falta la referencia de habitacion, domicilio o ubicacion.")
        }

        if (state.emergencyContactName.isBlank()) {
            errors += PatientValidationError(PatientField.EMERGENCY_CONTACT_NAME, "Falta el nombre del contacto principal.")
        } else if (!personNameRegex.matches(state.emergencyContactName.trim())) {
            errors += PatientValidationError(PatientField.EMERGENCY_CONTACT_NAME, "El nombre del contacto contiene caracteres no esperados.")
        }

        val phoneDigits = state.emergencyContactPhone.filter { it.isDigit() }
        if (state.emergencyContactPhone.isBlank()) {
            errors += PatientValidationError(PatientField.EMERGENCY_CONTACT_PHONE, "Falta el telefono del contacto principal.")
        } else if (phoneDigits.length !in 8..15) {
            errors += PatientValidationError(PatientField.EMERGENCY_CONTACT_PHONE, "El telefono del contacto debe tener entre 8 y 15 digitos.")
        }

        return errors
    }

    private fun Patient.toFormState(isEditMode: Boolean): PatientFormUiState {
        return PatientFormUiState(
            patientId = id,
            isEditMode = isEditMode,
            recordNotFound = false,
            fullName = fullName,
            preferredName = preferredName,
            age = age?.toString().orEmpty(),
            careContext = careContext,
            serviceLocationType = serviceLocationType,
            serviceAddress = serviceAddress,
            emergencyContactName = emergencyContactName,
            emergencyContactPhone = emergencyContactPhone,
            notes = notes,
            active = active
        )
    }

    companion object {
        fun factory(patientRepository: PatientRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PatientFormViewModel(
                        savedStateHandle = createSavedStateHandle(),
                        patientRepository = patientRepository
                    )
                }
            }
    }
}
