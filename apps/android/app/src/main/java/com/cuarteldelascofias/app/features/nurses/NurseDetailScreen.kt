package com.cuarteldelascofias.app.features.nurses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@Composable
fun NurseDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NurseDetailViewModel = viewModel(
        factory = NurseDetailViewModel.factory(AppContainer.nurseRepository)
    )
) {
    val nurse = viewModel.uiState.nurse

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (nurse == null) {
            Text(
                text = stringResource(R.string.nurse_not_found),
                style = MaterialTheme.typography.bodyLarge
            )
            return@Column
        }

        AppInfoCard(
            title = nurse.fullName,
            body = nurse.specialtyNotes
        )

        AppSectionTitle(text = stringResource(R.string.label_datos_relevantes))

        AppDetailRow(
            label = stringResource(R.string.nurse_label_telefono),
            value = nurse.phone
        )
        AppDetailRow(
            label = stringResource(R.string.nurse_label_zona),
            value = nurse.baseZone
        )
        AppDetailRow(
            label = stringResource(R.string.nurse_label_especialidad),
            value = nurse.specialtyNotes
        )
        AppDetailRow(
            label = stringResource(R.string.nurse_label_disponibilidad),
            value = nurse.availabilityLabel
        )
        AppDetailRow(
            label = stringResource(R.string.nurse_label_confianza),
            value = nurse.trustLevel
        )

        AppSectionTitle(text = stringResource(R.string.label_estado_actual))

        AppDetailRow(
            label = stringResource(R.string.patient_label_estado),
            value = nurse.activeStatusLabel()
        )

        AppSectionTitle(text = stringResource(R.string.nurse_label_notas))

        Text(
            text = nurse.notes,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
