package com.cuarteldelascofias.app.features.patients

import androidx.lifecycle.SavedStateHandle
import com.cuarteldelascofias.app.core.navigation.AppDestination
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.repository.PatientDraft
import com.cuarteldelascofias.app.data.repository.PatientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PatientFormViewModelTest {

    @Test
    fun savePatient_withMissingRequiredFields_returnsErrors() {
        val repository = FakePatientRepository()
        val viewModel = PatientFormViewModel(
            savedStateHandle = SavedStateHandle(),
            patientRepository = repository
        )

        val saved = viewModel.savePatient()

        assertFalse(saved)
        assertTrue(viewModel.uiState.hasError(PatientField.FULL_NAME))
        assertTrue(viewModel.uiState.hasError(PatientField.SERVICE_LOCATION_TYPE))
        assertTrue(viewModel.uiState.hasError(PatientField.SERVICE_ADDRESS))
        assertTrue(viewModel.uiState.hasError(PatientField.EMERGENCY_CONTACT_NAME))
        assertTrue(viewModel.uiState.hasError(PatientField.EMERGENCY_CONTACT_PHONE))
    }

    @Test
    fun savePatient_withInvalidLocationType_returnsValidationError() {
        val repository = FakePatientRepository()
        val viewModel = PatientFormViewModel(
            savedStateHandle = SavedStateHandle(),
            patientRepository = repository
        )

        viewModel.onFullNameChange("Maria Lopez")
        viewModel.onServiceLocationTypeChange("Hospital inventado")
        viewModel.onServiceAddressChange("Habitacion 201")
        viewModel.onEmergencyContactNameChange("Laura Lopez")
        viewModel.onEmergencyContactPhoneChange("5512345678")

        val saved = viewModel.savePatient()

        assertFalse(saved)
        assertTrue(viewModel.uiState.hasError(PatientField.SERVICE_LOCATION_TYPE))
    }

    @Test
    fun savePatient_withValidData_addsPatient() {
        val repository = FakePatientRepository()
        val viewModel = PatientFormViewModel(
            savedStateHandle = SavedStateHandle(),
            patientRepository = repository
        )

        viewModel.onFullNameChange("Maria Lopez")
        viewModel.onPreferredNameChange("Mary")
        viewModel.onAgeChange("68")
        viewModel.onCareContextChange("Recuperacion postquirurgica.")
        viewModel.onServiceLocationTypeChange("Hospital privado")
        viewModel.onServiceAddressChange("Habitacion 402")
        viewModel.onEmergencyContactNameChange("Laura Lopez")
        viewModel.onEmergencyContactPhoneChange("55 1234 5678")
        viewModel.onNotesChange("Sin novedades.")

        val saved = viewModel.savePatient()

        assertTrue(saved)
        assertEquals(1, repository.patients.value.size)
        val savedPatient = repository.patients.value.first()
        assertEquals("Maria Lopez", savedPatient.fullName)
        assertEquals("Mary", savedPatient.preferredName)
        assertEquals("Hospital privado", savedPatient.serviceLocationType)
        assertEquals("Habitacion 402", savedPatient.serviceAddress)
    }

    @Test
    fun editMode_loadsExistingPatientAndUpdatesIt() {
        val existingPatient = Patient(
            id = "patient-1",
            fullName = "Maria Lopez",
            preferredName = "Maria",
            age = 68,
            careContext = "Guardia nocturna.",
            serviceLocationType = "Hospital privado",
            serviceAddress = "Habitacion 402",
            emergencyContactName = "Laura Lopez",
            emergencyContactPhone = "5512345678",
            active = true,
            notes = "Sin notas operativas por ahora.",
            activeShiftCount = 2
        )
        val repository = FakePatientRepository(existingPatients = listOf(existingPatient))
        val viewModel = PatientFormViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(AppDestination.PatientEdit.patientIdArg to existingPatient.id)
            ),
            patientRepository = repository
        )

        assertTrue(viewModel.uiState.isEditMode)
        assertEquals(existingPatient.fullName, viewModel.uiState.fullName)
        assertEquals(existingPatient.serviceAddress, viewModel.uiState.serviceAddress)

        viewModel.onPreferredNameChange("Mari")
        viewModel.onServiceAddressChange("Habitacion 410")
        viewModel.onNotesChange("Actualizado.")

        val saved = viewModel.savePatient()

        assertTrue(saved)
        val updatedPatient = repository.getPatientById(existingPatient.id)
        assertNotNull(updatedPatient)
        assertEquals(existingPatient.id, updatedPatient?.id)
        assertEquals("Mari", updatedPatient?.preferredName)
        assertEquals("Habitacion 410", updatedPatient?.serviceAddress)
        assertEquals(2, updatedPatient?.activeShiftCount)
    }
}

private class FakePatientRepository(
    existingPatients: List<Patient> = emptyList()
) : PatientRepository {
    private val patientsState = MutableStateFlow(existingPatients)

    override val patients: StateFlow<List<Patient>> = patientsState

    override fun getPatientById(patientId: String): Patient? {
        return patientsState.value.firstOrNull { it.id == patientId }
    }

    override fun addPatient(draft: PatientDraft): Patient {
        val patient = Patient(
            id = "patient-${patientsState.value.size + 1}",
            fullName = draft.fullName.trim(),
            preferredName = draft.preferredName.trim().ifBlank { draft.fullName.trim() },
            age = draft.age,
            careContext = draft.careContext.trim(),
            serviceLocationType = draft.serviceLocationType.trim(),
            serviceAddress = draft.serviceAddress.trim(),
            emergencyContactName = draft.emergencyContactName.trim(),
            emergencyContactPhone = draft.emergencyContactPhone.trim(),
            active = draft.active,
            notes = draft.notes.trim().ifBlank { "Sin notas operativas por ahora." },
            activeShiftCount = 0
        )

        patientsState.value = listOf(patient) + patientsState.value
        return patient
    }

    override fun updatePatient(patientId: String, draft: PatientDraft): Patient? {
        val existingPatient = getPatientById(patientId) ?: return null
        val updatedPatient = existingPatient.copy(
            fullName = draft.fullName.trim(),
            preferredName = draft.preferredName.trim().ifBlank { draft.fullName.trim() },
            age = draft.age,
            careContext = draft.careContext.trim(),
            serviceLocationType = draft.serviceLocationType.trim(),
            serviceAddress = draft.serviceAddress.trim(),
            emergencyContactName = draft.emergencyContactName.trim(),
            emergencyContactPhone = draft.emergencyContactPhone.trim(),
            active = draft.active,
            notes = draft.notes.trim().ifBlank { "Sin notas operativas por ahora." }
        )

        patientsState.value = patientsState.value.map { patient ->
            if (patient.id == patientId) updatedPatient else patient
        }
        return updatedPatient
    }
}
