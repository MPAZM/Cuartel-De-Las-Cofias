package com.cuarteldelascofias.app.core.navigation

import androidx.annotation.StringRes
import com.cuarteldelascofias.app.R

sealed class AppDestination(
    val route: String,
    @param:StringRes val titleRes: Int,
    val isTopLevel: Boolean = false
) {
    data object Dashboard : AppDestination(
        route = "dashboard",
        titleRes = R.string.title_inicio,
        isTopLevel = true
    )

    data object Shifts : AppDestination(
        route = "shifts",
        titleRes = R.string.title_guardias,
        isTopLevel = true
    )

    data object ShiftDetail : AppDestination(
        route = "shift_detail",
        titleRes = R.string.title_detalle_guardia
    ) {
        const val shiftIdArg = "shiftId"
        val routeWithArgs = "$route/{$shiftIdArg}"

        fun createRoute(shiftId: String): String = "$route/$shiftId"
    }

    data object Patients : AppDestination(
        route = "patients",
        titleRes = R.string.title_pacientes,
        isTopLevel = true
    )

    data object PatientDetail : AppDestination(
        route = "patient_detail",
        titleRes = R.string.title_detalle_paciente
    ) {
        const val patientIdArg = "patientId"
        val routeWithArgs = "$route/{$patientIdArg}"

        fun createRoute(patientId: String): String = "$route/$patientId"
    }

    data object PatientForm : AppDestination(
        route = "patient_form",
        titleRes = R.string.title_nuevo_paciente
    )

    data object PatientEdit : AppDestination(
        route = "patient_edit",
        titleRes = R.string.title_editar_paciente
    ) {
        const val patientIdArg = "patientId"
        val routeWithArgs = "$route/{$patientIdArg}"

        fun createRoute(patientId: String): String = "$route/$patientId"
    }

    data object Nurses : AppDestination(
        route = "nurses",
        titleRes = R.string.title_enfermeras,
        isTopLevel = true
    )

    data object NurseForm : AppDestination(
        route = "nurse_form",
        titleRes = R.string.title_nueva_enfermera
    )

    data object NurseDetail : AppDestination(
        route = "nurse_detail",
        titleRes = R.string.title_detalle_enfermera
    ) {
        const val nurseIdArg = "nurseId"
        val routeWithArgs = "$route/{$nurseIdArg}"

        fun createRoute(nurseId: String): String = "$route/$nurseId"
    }

    data object Billing : AppDestination(
        route = "billing",
        titleRes = R.string.title_cobros,
        isTopLevel = true
    )

    data object ShiftForm : AppDestination(
        route = "shift_form",
        titleRes = R.string.title_nueva_guardia
    )

    data object ShiftReport : AppDestination(
        route = "shift_report",
        titleRes = R.string.title_reporte_guardia
    ) {
        const val shiftIdArg = "shiftId"
        val routeWithArgs = "$route/{$shiftIdArg}"

        fun createRoute(shiftId: String): String = "$route/$shiftId"
    }

    companion object {
        val all = listOf(
            Dashboard,
            Shifts,
            ShiftDetail,
            Patients,
            PatientDetail,
            PatientForm,
            PatientEdit,
            Nurses,
            NurseForm,
            NurseDetail,
            Billing,
            ShiftForm,
            ShiftReport
        )

        fun fromRoute(route: String?): AppDestination? {
            return all.firstOrNull { destination ->
                route == destination.route || route?.startsWith("${destination.route}/") == true
            }
        }
    }
}
