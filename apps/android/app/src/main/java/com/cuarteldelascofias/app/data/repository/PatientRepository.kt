package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.Patient
import kotlinx.coroutines.flow.StateFlow

data class PatientDraft(
    val fullName: String,
    val preferredName: String,
    val age: Int?,
    val careContext: String,
    val serviceLocationType: String,
    val serviceAddress: String,
    val emergencyContactName: String,
    val emergencyContactPhone: String,
    val active: Boolean,
    val notes: String
)

interface PatientRepository {
    val patients: StateFlow<List<Patient>>
    fun getPatientById(patientId: String): Patient?
    fun addPatient(draft: PatientDraft): Patient
    fun updatePatient(patientId: String, draft: PatientDraft): Patient?
}
