package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.Nurse
import kotlinx.coroutines.flow.StateFlow

data class NurseDraft(
    val fullName: String,
    val phone: String,
    val specialtyNotes: String,
    val baseZone: String,
    val availabilityLabel: String,
    val active: Boolean,
    val trustLevel: String,
    val notes: String
)

interface NurseRepository {
    val nurses: StateFlow<List<Nurse>>
    fun getNurseById(nurseId: String): Nurse?
    fun addNurse(draft: NurseDraft): Nurse
}
