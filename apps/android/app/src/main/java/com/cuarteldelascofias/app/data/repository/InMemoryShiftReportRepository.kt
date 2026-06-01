package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.ShiftReport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object InMemoryShiftReportRepository : ShiftReportRepository {
    private val reportsState = MutableStateFlow(
        listOf(
            ShiftReport(
                id = "report-shift-maria-night-1",
                shiftId = "shift-maria-night-1",
                generalStatus = "La paciente se mantuvo orientada y estable durante toda la guardia nocturna.",
                medicationNotes = "Se apoyo en la administracion de medicamentos indicados por enfermeria del hospital y se dio seguimiento a horarios.",
                vitalSignsNotes = "Sin cambios relevantes reportados durante el turno. Se registro toma de signos antes de medianoche.",
                relevantEvents = "La paciente refirio molestia leve al cambio de posicion, sin complicaciones posteriores.",
                hospitalStaffComments = "El personal de piso indico continuar vigilancia nocturna y avisar si aumentaba el dolor.",
                familyComments = "La familia solicito avisar solo en caso de alguna incidencia relevante.",
                handoffSummary = "Se entrega turno con paciente estable, comoda y con medicamentos cubiertos conforme a indicacion.",
                createdAtLabel = "23 mayo - 7:50 a. m."
            ),
            ShiftReport(
                id = "report-shift-jose-day-1",
                shiftId = "shift-jose-day-1",
                generalStatus = "El paciente se observo tranquilo y colaborador durante la guardia diurna.",
                medicationNotes = "Se acompanaron horarios de medicamentos y alimentacion conforme a indicaciones medicas.",
                vitalSignsNotes = "Se reportan signos estables durante el turno, sin alteraciones comunicadas por el personal.",
                relevantEvents = "No se presentaron incidencias mayores.",
                hospitalStaffComments = "El medico de guardia indico mantener acompanamiento y observacion.",
                familyComments = "La familia fue informada al cierre del turno sin novedades importantes.",
                handoffSummary = "Turno concluido sin incidencias. Se deja al paciente estable y acompanado.",
                createdAtLabel = "22 mayo - 7:40 p. m."
            )
        )
    )

    override val reports: StateFlow<List<ShiftReport>> = reportsState.asStateFlow()

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

        reportsState.value = reportsState.value
            .filterNot { it.shiftId == draft.shiftId }
            .plus(report)
            .sortedBy { it.createdAtLabel }

        return report
    }

    private val dateFormatter = SimpleDateFormat("d MMM yyyy, h:mm a", Locale.forLanguageTag("es-MX"))
}
