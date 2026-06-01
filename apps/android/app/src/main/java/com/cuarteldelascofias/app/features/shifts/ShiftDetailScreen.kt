package com.cuarteldelascofias.app.features.shifts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle
import com.cuarteldelascofias.app.core.designsystem.AppStatusChip

@Composable
fun ShiftDetailScreen(
    onOpenReport: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShiftDetailViewModel = viewModel(
        factory = ShiftDetailViewModel.factory(
            shiftRepository = AppContainer.shiftRepository,
            patientRepository = AppContainer.patientRepository
        )
    )
) {
    val shift = viewModel.uiState.shift
    val patient = viewModel.uiState.patient

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (shift == null) {
            Text(
                text = stringResource(R.string.shift_not_found),
                style = MaterialTheme.typography.bodyLarge
            )
            return@Column
        }

        AppInfoCard(
            title = patient?.displayName ?: stringResource(R.string.shift_label_paciente),
            body = "${shift.serviceType} en ${shift.locationSummary}"
        )

        AppStatusChip(text = shift.status.label())

        AppSectionTitle(text = stringResource(R.string.label_datos_relevantes))

        AppDetailRow(
            label = stringResource(R.string.shift_label_paciente),
            value = patient?.displayName ?: "No disponible"
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_enfermera),
            value = shift.nurseName ?: stringResource(R.string.shift_pending_assignment)
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_tipo_servicio),
            value = shift.serviceType
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_ubicacion),
            value = shift.locationSummary
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_inicio),
            value = shift.startLabel
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_fin),
            value = shift.endLabel
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_tarifa),
            value = shift.rateAmount
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_comision),
            value = shift.commissionAmount
        )
        AppDetailRow(
            label = stringResource(R.string.shift_label_pago_neto),
            value = shift.nurseNetAmount
        )

        AppSectionTitle(text = stringResource(R.string.label_resumen_operativo))

        Text(
            text = stringResource(R.string.shift_summary_info),
            style = MaterialTheme.typography.bodyMedium
        )

        AppSectionTitle(text = stringResource(R.string.shift_label_notas_admin))

        Text(
            text = shift.adminNotes,
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = { onOpenReport(shift.id) }) {
            Text(text = stringResource(R.string.accion_abrir_reporte))
        }
    }
}
