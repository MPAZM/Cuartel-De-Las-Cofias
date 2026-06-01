package com.cuarteldelascofias.app.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.PersonalInjury
import androidx.compose.material.icons.outlined.SpaceDashboard
import androidx.compose.ui.graphics.vector.ImageVector
import com.cuarteldelascofias.app.R

data class TopLevelDestination(
    val route: String,
    @param:StringRes val labelRes: Int,
    val icon: ImageVector
)

object TopLevelDestinations {
    val items = listOf(
        TopLevelDestination(
            route = AppDestination.Dashboard.route,
            labelRes = R.string.nav_inicio,
            icon = Icons.Outlined.SpaceDashboard
        ),
        TopLevelDestination(
            route = AppDestination.Shifts.route,
            labelRes = R.string.nav_guardias,
            icon = Icons.Outlined.CalendarMonth
        ),
        TopLevelDestination(
            route = AppDestination.Patients.route,
            labelRes = R.string.nav_pacientes,
            icon = Icons.Outlined.PersonalInjury
        ),
        TopLevelDestination(
            route = AppDestination.Nurses.route,
            labelRes = R.string.nav_enfermeras,
            icon = Icons.Outlined.MedicalServices
        ),
        TopLevelDestination(
            route = AppDestination.Billing.route,
            labelRes = R.string.nav_cobros,
            icon = Icons.Outlined.Payments
        )
    )
}
