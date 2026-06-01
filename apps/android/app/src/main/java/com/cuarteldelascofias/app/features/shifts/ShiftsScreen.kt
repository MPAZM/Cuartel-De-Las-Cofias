package com.cuarteldelascofias.app.features.shifts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.core.common.AppContainer
import com.cuarteldelascofias.app.core.designsystem.AppDetailRow
import com.cuarteldelascofias.app.core.designsystem.AppInfoCard
import com.cuarteldelascofias.app.core.designsystem.AppMetricCard
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle
import com.cuarteldelascofias.app.core.designsystem.AppStatusChip

@Composable
fun ShiftsScreen(
    onCreateShiftClick: () -> Unit,
    onShiftClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShiftsViewModel = viewModel(
        factory = ShiftsViewModel.factory(
            shiftRepository = AppContainer.shiftRepository,
            patientRepository = AppContainer.patientRepository
        )
    )
) {
    val uiState = viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.shifts_intro),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onCreateShiftClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.accion_nueva_guardia))
        }

        AppSectionTitle(text = stringResource(R.string.nav_guardias))

        AppMetricCard(
            label = stringResource(R.string.label_guardias_totales),
            value = uiState.shifts.size.toString()
        )

        AppMetricCard(
            label = stringResource(R.string.label_guardias_activas),
            value = uiState.activeShiftsCount.toString()
        )

        if (uiState.shifts.isEmpty()) {
            Text(
                text = stringResource(R.string.shifts_empty),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        uiState.shifts.forEach { shift ->
            AppInfoCard(
                title = shift.patientName,
                body = shift.locationSummary,
                actionLabel = stringResource(R.string.accion_ver_detalle),
                onActionClick = { onShiftClick(shift.id) }
            )
            AppDetailRow(
                label = stringResource(R.string.shift_label_enfermera),
                value = shift.nurseName
            )
            AppDetailRow(
                label = stringResource(R.string.shift_label_horario),
                value = shift.scheduleSummary
            )
            AppStatusChip(text = shift.status.label())
            HorizontalDivider()
        }
    }
}
