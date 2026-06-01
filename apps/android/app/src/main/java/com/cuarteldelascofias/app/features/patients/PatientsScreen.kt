package com.cuarteldelascofias.app.features.patients

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
fun PatientsScreen(
    onCreatePatientClick: () -> Unit,
    onPatientClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PatientsViewModel = viewModel(
        factory = PatientsViewModel.factory(AppContainer.patientRepository)
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
        Text(text = stringResource(R.string.patients_intro), style = MaterialTheme.typography.bodyLarge)

        Button(
            onClick = onCreatePatientClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.accion_nuevo_paciente))
        }

        AppSectionTitle(text = stringResource(R.string.nav_pacientes))

        AppMetricCard(
            label = stringResource(R.string.patients_total),
            value = uiState.patients.size.toString()
        )

        AppMetricCard(
            label = stringResource(R.string.patients_active),
            value = uiState.activePatientsCount.toString()
        )

        if (uiState.patients.isEmpty()) {
            Text(
                text = stringResource(R.string.patients_empty),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        uiState.patients.forEach { patient ->
            AppInfoCard(
                title = patient.displayName,
                body = patient.careContext,
                actionLabel = stringResource(R.string.accion_ver_detalle),
                onActionClick = { onPatientClick(patient.id) }
            )
            AppDetailRow(
                label = stringResource(R.string.patient_label_contacto),
                value = patient.contactSummary
            )
            AppDetailRow(
                label = stringResource(R.string.patient_label_estado),
                value = patient.activeStatusLabel()
            )
            AppDetailRow(
                label = stringResource(R.string.patient_label_ubicacion),
                value = patient.locationSummary
            )
            AppDetailRow(
                label = stringResource(R.string.nav_guardias),
                value = patient.shiftSummary()
            )
            HorizontalDivider()
        }
    }
}
