package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.local.PatientDao
import com.cuarteldelascofias.app.data.local.ShiftDao
import com.cuarteldelascofias.app.data.local.toEntity
import com.cuarteldelascofias.app.data.local.toModel
import com.cuarteldelascofias.app.data.model.Shift
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class RoomShiftRepository(
    shiftDao: ShiftDao,
    private val patientDao: PatientDao,
    private val scope: CoroutineScope
) : ShiftRepository {
    private val shiftsState = MutableStateFlow<List<Shift>>(emptyList())

    override val shifts: StateFlow<List<Shift>> = shiftsState.asStateFlow()

    private val dao = shiftDao

    init {
        scope.launch {
            dao.observeShifts().collect { entities ->
                shiftsState.value = entities.map { it.toModel() }
            }
        }
    }

    override fun getShiftById(shiftId: String): Shift? {
        return shiftsState.value.firstOrNull { it.id == shiftId }
    }

    override fun addShift(draft: ShiftDraft): Shift {
        val nurseNet = (draft.rateAmount - draft.commissionAmount).coerceAtLeast(0)
        val shift = Shift(
            id = buildShiftId(draft.patientId),
            patientId = draft.patientId,
            nurseName = draft.nurseName?.trim()?.ifBlank { null },
            serviceType = draft.serviceType.trim(),
            locationSummary = draft.locationSummary.trim(),
            startLabel = draft.startLabel.trim(),
            endLabel = draft.endLabel.trim(),
            scheduleSummary = draft.scheduleSummary.trim(),
            rateAmount = formatCurrency(draft.rateAmount),
            commissionAmount = formatCurrency(draft.commissionAmount),
            nurseNetAmount = formatCurrency(nurseNet),
            status = draft.status,
            adminNotes = draft.adminNotes.trim().ifBlank { "Sin notas administrativas por ahora." }
        )

        scope.launch {
            dao.insert(shift.toEntity())
            val activeShiftCount = dao.countActiveShiftsForPatient(draft.patientId)
            patientDao.updateActiveShiftCount(draft.patientId, activeShiftCount)
        }
        return shift
    }

    private fun buildShiftId(patientId: String): String {
        val suffix = shiftsState.value.count { it.patientId == patientId } + 1
        return "shift-$patientId-$suffix"
    }

    private fun formatCurrency(amount: Int): String {
        return "${currencyFormatter.format(amount)} MXN"
    }

    companion object {
        private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("es-MX")
        ).apply {
            maximumFractionDigits = 0
        }
    }
}
