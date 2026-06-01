package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.local.ShiftReportDao
import com.cuarteldelascofias.app.data.local.toEntity
import com.cuarteldelascofias.app.data.local.toModel
import com.cuarteldelascofias.app.data.model.ShiftReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomShiftReportRepository(
    shiftReportDao: ShiftReportDao,
    private val scope: CoroutineScope
) : ShiftReportRepository {
    private val reportsState = MutableStateFlow<List<ShiftReport>>(emptyList())
    private val dao = shiftReportDao

    override val reports: StateFlow<List<ShiftReport>> = reportsState.asStateFlow()

    init {
        scope.launch {
            dao.observeReports().collect { entities ->
                reportsState.value = entities.map { it.toModel() }
            }
        }
    }

    override fun getReportByShiftId(shiftId: String): ShiftReport? {
        return reportsState.value.firstOrNull { it.shiftId == shiftId }
    }

    override fun upsertReport(draft: ShiftReportDraft): ShiftReport {
        val existing = getReportByShiftId(draft.shiftId)
        val report = ShiftReport(
            id = existing?.id ?: "report-${draft.shiftId}",
            shiftId = draft.shiftId,
            generalStatus = draft.generalStatus.trim(),
            medicationNotes = draft.medicationNotes.trim(),
            vitalSignsNotes = draft.vitalSignsNotes.trim(),
            relevantEvents = draft.relevantEvents.trim(),
            hospitalStaffComments = draft.hospitalStaffComments.trim(),
            familyComments = draft.familyComments.trim(),
            handoffSummary = draft.handoffSummary.trim(),
            createdAtLabel = existing?.createdAtLabel ?: dateFormatter.format(Date())
        )

        scope.launch {
            dao.insert(report.toEntity())
        }
        return report
    }

    companion object {
        private val dateFormatter = SimpleDateFormat("d MMM yyyy, h:mm a", Locale.forLanguageTag("es-MX"))
    }
}
