package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.ShiftReport
import kotlinx.coroutines.flow.StateFlow

data class ShiftReportDraft(
    val shiftId: String,
    val generalStatus: String,
    val medicationNotes: String,
    val vitalSignsNotes: String,
    val relevantEvents: String,
    val hospitalStaffComments: String,
    val familyComments: String,
    val handoffSummary: String
)

interface ShiftReportRepository {
    val reports: StateFlow<List<ShiftReport>>
    fun getReportByShiftId(shiftId: String): ShiftReport?
    fun upsertReport(draft: ShiftReportDraft): ShiftReport
}
