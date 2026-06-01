package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.Shift
import com.cuarteldelascofias.app.data.model.ShiftStatus
import kotlinx.coroutines.flow.StateFlow

data class ShiftDraft(
    val patientId: String,
    val nurseName: String?,
    val serviceType: String,
    val locationSummary: String,
    val startLabel: String,
    val endLabel: String,
    val scheduleSummary: String,
    val rateAmount: Int,
    val commissionAmount: Int,
    val status: ShiftStatus,
    val adminNotes: String
)

interface ShiftRepository {
    val shifts: StateFlow<List<Shift>>
    fun getShiftById(shiftId: String): Shift?
    fun addShift(draft: ShiftDraft): Shift
}
