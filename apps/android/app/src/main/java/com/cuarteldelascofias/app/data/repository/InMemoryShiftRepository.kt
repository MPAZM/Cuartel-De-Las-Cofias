package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.Shift
import com.cuarteldelascofias.app.data.model.ShiftStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import java.util.Locale

object InMemoryShiftRepository : ShiftRepository {
    private val shiftsState = MutableStateFlow(
        listOf(
            Shift(
                id = "shift-maria-night-1",
                patientId = "patient-maria-lopez",
                nurseName = "Lic. Andrea Perez",
                serviceType = "Guardia nocturna",
                locationSummary = "Hospital Angeles - Habitacion 402",
                startLabel = "22 mayo - 8:00 p. m.",
                endLabel = "23 mayo - 8:00 a. m.",
                scheduleSummary = "Hoy - 8:00 p. m. a 8:00 a. m.",
                rateAmount = "$1,200 MXN",
                commissionAmount = "$200 MXN",
                nurseNetAmount = "$1,000 MXN",
                status = ShiftStatus.IN_PROGRESS,
                adminNotes = "Confirmar reporte final con la familia al cierre del turno."
            ),
            Shift(
                id = "shift-jose-day-1",
                patientId = "patient-jose-rivera",
                nurseName = "Lic. Karla Sanchez",
                serviceType = "Guardia diurna",
                locationSummary = "Hospital General - Piso 3 - Cama 18",
                startLabel = "22 mayo - 8:00 a. m.",
                endLabel = "22 mayo - 8:00 p. m.",
                scheduleSummary = "Hoy - 8:00 a. m. a 8:00 p. m.",
                rateAmount = "$1,000 MXN",
                commissionAmount = "$150 MXN",
                nurseNetAmount = "$850 MXN",
                status = ShiftStatus.COMPLETED,
                adminNotes = "La guardia termino sin incidencias mayores."
            ),
            Shift(
                id = "shift-ana-night-1",
                patientId = "patient-ana-ortega",
                nurseName = null,
                serviceType = "Guardia nocturna",
                locationSummary = "Casa particular - Col. Del Valle",
                startLabel = "23 mayo - 8:00 p. m.",
                endLabel = "24 mayo - 8:00 a. m.",
                scheduleSummary = "Manana - 8:00 p. m. a 8:00 a. m.",
                rateAmount = "$900 MXN",
                commissionAmount = "$150 MXN",
                nurseNetAmount = "$750 MXN",
                status = ShiftStatus.PENDING,
                adminNotes = "Falta asignar enfermera y confirmar acceso al domicilio."
            )
        )
    )

    override val shifts: StateFlow<List<Shift>> = shiftsState.asStateFlow()

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

        shiftsState.value = listOf(shift) + shiftsState.value
        return shift
    }

    private fun buildShiftId(patientId: String): String {
        val suffix = shiftsState.value.count { it.patientId == patientId } + 1
        return "shift-$patientId-$suffix"
    }

    private fun formatCurrency(amount: Int): String {
        return "${currencyFormatter.format(amount)} MXN"
    }

    private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(
        Locale.forLanguageTag("es-MX")
    ).apply {
        maximumFractionDigits = 0
    }
}
