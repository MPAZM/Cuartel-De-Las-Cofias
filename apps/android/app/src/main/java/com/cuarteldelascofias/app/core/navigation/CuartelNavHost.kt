package com.cuarteldelascofias.app.core.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cuarteldelascofias.app.features.billing.BillingScreen
import com.cuarteldelascofias.app.features.dashboard.DashboardScreen
import com.cuarteldelascofias.app.features.nurses.NurseDetailScreen
import com.cuarteldelascofias.app.features.nurses.NurseFormScreen
import com.cuarteldelascofias.app.features.nurses.NursesScreen
import com.cuarteldelascofias.app.features.patients.PatientDetailScreen
import com.cuarteldelascofias.app.features.patients.PatientFormScreen
import com.cuarteldelascofias.app.features.patients.PatientsScreen
import com.cuarteldelascofias.app.features.reports.ShiftReportScreen
import com.cuarteldelascofias.app.features.shifts.ShiftDetailScreen
import com.cuarteldelascofias.app.features.shifts.ShiftFormScreen
import com.cuarteldelascofias.app.features.shifts.ShiftsScreen

@androidx.compose.runtime.Composable
fun CuartelNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.Dashboard.route,
        modifier = modifier
    ) {
        composable(AppDestination.Dashboard.route) {
            DashboardScreen(
                onGuardiasClick = {
                    navController.navigate(AppDestination.Shifts.route)
                },
                onPacientesClick = {
                    navController.navigate(AppDestination.Patients.route)
                },
                onEnfermerasClick = {
                    navController.navigate(AppDestination.Nurses.route)
                },
                onCobrosClick = {
                    navController.navigate(AppDestination.Billing.route)
                }
            )
        }

        composable(AppDestination.Shifts.route) {
            ShiftsScreen(
                onCreateShiftClick = {
                    navController.navigate(AppDestination.ShiftForm.route)
                },
                onShiftClick = { shiftId ->
                    navController.navigate(AppDestination.ShiftDetail.createRoute(shiftId))
                }
            )
        }

        composable(AppDestination.ShiftForm.route) {
            ShiftFormScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AppDestination.ShiftDetail.routeWithArgs,
            arguments = listOf(
                navArgument(AppDestination.ShiftDetail.shiftIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            ShiftDetailScreen(
                onOpenReport = { shiftId ->
                    navController.navigate(AppDestination.ShiftReport.createRoute(shiftId))
                }
            )
        }

        composable(AppDestination.Patients.route) {
            PatientsScreen(
                onCreatePatientClick = {
                    navController.navigate(AppDestination.PatientForm.route)
                },
                onPatientClick = { patientId ->
                    navController.navigate(AppDestination.PatientDetail.createRoute(patientId))
                }
            )
        }

        composable(AppDestination.PatientForm.route) {
            PatientFormScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AppDestination.PatientEdit.routeWithArgs,
            arguments = listOf(
                navArgument(AppDestination.PatientEdit.patientIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            PatientFormScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AppDestination.PatientDetail.routeWithArgs,
            arguments = listOf(
                navArgument(AppDestination.PatientDetail.patientIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            PatientDetailScreen(
                onEditPatient = { patientId ->
                    navController.navigate(AppDestination.PatientEdit.createRoute(patientId))
                }
            )
        }

        composable(AppDestination.Nurses.route) {
            NursesScreen(
                onCreateNurseClick = {
                    navController.navigate(AppDestination.NurseForm.route)
                },
                onNurseClick = { nurseId ->
                    navController.navigate(AppDestination.NurseDetail.createRoute(nurseId))
                }
            )
        }

        composable(AppDestination.NurseForm.route) {
            NurseFormScreen(
                onCancel = {
                    navController.popBackStack()
                },
                onSaved = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = AppDestination.NurseDetail.routeWithArgs,
            arguments = listOf(
                navArgument(AppDestination.NurseDetail.nurseIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            NurseDetailScreen()
        }

        composable(AppDestination.Billing.route) {
            BillingScreen()
        }

        composable(
            route = AppDestination.ShiftReport.routeWithArgs,
            arguments = listOf(
                navArgument(AppDestination.ShiftReport.shiftIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            ShiftReportScreen()
        }
    }
}
