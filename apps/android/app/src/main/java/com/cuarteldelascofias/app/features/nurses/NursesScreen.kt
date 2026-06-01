package com.cuarteldelascofias.app.features.nurses

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

@Composable
fun NursesScreen(
    onCreateNurseClick: () -> Unit,
    onNurseClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NursesViewModel = viewModel(
        factory = NursesViewModel.factory(AppContainer.nurseRepository)
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
            text = stringResource(R.string.nurses_intro),
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onCreateNurseClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.accion_nueva_enfermera))
        }

        AppSectionTitle(text = stringResource(R.string.nav_enfermeras))

        AppMetricCard(
            label = stringResource(R.string.nurses_total),
            value = uiState.nurses.size.toString()
        )

        AppMetricCard(
            label = stringResource(R.string.nurses_active),
            value = uiState.activeNursesCount.toString()
        )

        if (uiState.nurses.isEmpty()) {
            Text(
                text = stringResource(R.string.nurses_empty),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        uiState.nurses.forEach { nurse ->
            AppInfoCard(
                title = nurse.fullName,
                body = nurse.specialtyNotes,
                actionLabel = stringResource(R.string.accion_ver_detalle),
                onActionClick = { onNurseClick(nurse.id) }
            )
            AppDetailRow(
                label = stringResource(R.string.nurse_label_zona),
                value = nurse.baseZone
            )
            AppDetailRow(
                label = stringResource(R.string.nurse_label_disponibilidad),
                value = nurse.availabilityLabel
            )
            AppDetailRow(
                label = stringResource(R.string.patient_label_estado),
                value = nurse.activeStatusLabel()
            )
            HorizontalDivider()
        }
    }
}
