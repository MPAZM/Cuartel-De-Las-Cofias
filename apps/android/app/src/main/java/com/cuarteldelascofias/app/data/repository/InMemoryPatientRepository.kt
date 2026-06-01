package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.Patient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object InMemoryPatientRepository : PatientRepository {
    private val patientsState = MutableStateFlow(
        listOf(
            Patient(
                id = "patient-maria-lopez",
                fullName = "Maria del Carmen Lopez",
                preferredName = "Maria del Carmen Lopez",
                age = 68,
                careContext = "Recuperacion postquirurgica con acompanamiento nocturno.",
                serviceLocationType = "Hospital Angeles",
                serviceAddress = "Habitacion 402",
                emergencyContactName = "Laura Lopez",
                emergencyContactPhone = "55 1234 5678",
                active = true,
                notes = "Paciente sensible al ruido. La familia pide actualizar solo al cierre del turno.",
                activeShiftCount = 2
            ),
            Patient(
                id = "patient-jose-rivera",
                fullName = "Jose Manuel Rivera",
                preferredName = "Jose Rivera",
                age = 74,
                careContext = "Acompanamiento hospitalario y seguimiento de indicaciones medicas.",
                serviceLocationType = "Hospital General",
                serviceAddress = "Piso 3 - Cama 18",
                emergencyContactName = "Monica Rivera",
                emergencyContactPhone = "55 9876 5432",
                active = true,
                notes = "El familiar solicita confirmar toma de signos vitales al menos una vez por turno.",
                activeShiftCount = 1
            ),
            Patient(
                id = "patient-ana-ortega",
                fullName = "Ana Luisa Ortega",
                preferredName = "Ana Ortega",
                age = 59,
                careContext = "Cuidado nocturno en casa y apoyo durante recuperacion.",
                serviceLocationType = "Casa particular",
                serviceAddress = "Col. Del Valle",
                emergencyContactName = "Carlos Ortega",
                emergencyContactPhone = "55 2222 1111",
                active = false,
                notes = "Caso pausado. La familia podria reactivar guardias el proximo fin de semana.",
                activeShiftCount = 0
            )
        )
    )

    override val patients: StateFlow<List<Patient>> = patientsState.asStateFlow()

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
