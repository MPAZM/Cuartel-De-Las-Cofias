package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.local.PatientDao
import com.cuarteldelascofias.app.data.local.toEntity
import com.cuarteldelascofias.app.data.local.toModel
import com.cuarteldelascofias.app.data.model.Patient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomPatientRepository(
    patientDao: PatientDao,
    private val scope: CoroutineScope
) : PatientRepository {
    private val patientsState = MutableStateFlow<List<Patient>>(emptyList())

    override val patients: StateFlow<List<Patient>> = patientsState.asStateFlow()

    init {
        scope.launch {
            patientDao.observePatients().collect { entities ->
                patientsState.value = entities.map { it.toModel() }
            }
        }
    }

    private val dao = patientDao

    override fun getPatientById(patientId: String): Patient? {
        return patientsState.value.firstOrNull { it.id == patientId }
    }

    override fun addPatient(draft: PatientDraft): Patient {
        val patient = Patient(
            id = buildPatientId(draft.fullName),
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

        scope.launch {
            dao.insert(patient.toEntity())
        }
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

        scope.launch {
            dao.insert(updatedPatient.toEntity())
        }
        return updatedPatient
    }

    private fun buildPatientId(fullName: String): String {
        val baseSlug = fullName
            .trim()
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), "-")
            .trim('-')
            .ifBlank { "patient" }

        val existingMatches = patientsState.value.count { it.id.startsWith(baseSlug) }
        return "$baseSlug-${existingMatches + 1}"
    }
}
