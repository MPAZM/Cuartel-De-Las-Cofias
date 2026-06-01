package com.cuarteldelascofias.app.features.patients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.core.common.AppContainer
import com.cuarteldelascofias.app.core.designsystem.AppDetailRow
import com.cuarteldelascofias.app.core.designsystem.AppInfoCard
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PatientDetailScreen(
    onEditPatient: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PatientDetailViewModel = viewModel(
        factory = PatientDetailViewModel.factory(AppContainer.patientRepository)
    )
) {
    val patient = viewModel.uiState.patient

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (patient == null) {
            Text(
                text = stringResource(R.string.patient_not_found),
                style = MaterialTheme.typography.bodyLarge
            )
            return@Column
        }

        AppInfoCard(
            title = patient.displayName,
            body = patient.careContext
        )

        Button(
            onClick = { onEditPatient(patient.id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.accion_editar))
        }

        AppSectionTitle(text = stringResource(R.string.label_datos_relevantes))

        AppDetailRow(
            label = stringResource(R.string.patient_label_edad),
            value = patient.age?.let { stringResource(R.string.patient_age_years, it) }
                ?: stringResource(R.string.patient_age_unspecified)
        )
        AppDetailRow(
            label = stringResource(R.string.patient_label_ubicacion),
            value = patient.locationSummary
        )
        AppDetailRow(
            label = stringResource(R.string.patient_label_contacto),
            value = patient.contactSummary
        )
        AppDetailRow(
            label = stringResource(R.string.patient_label_contexto),
            value = patient.careContext
        )

        AppSectionTitle(text = stringResource(R.string.label_estado_actual))

        AppDetailRow(
            label = stringResource(R.string.patient_label_estado),
            value = patient.activeStatusLabel()
        )
        AppDetailRow(
            label = stringResource(R.string.nav_guardias),
            value = patient.shiftSummary()
        )

        AppSectionTitle(text = stringResource(R.string.patient_label_notas))

        Text(text = patient.notes, style = MaterialTheme.typography.bodyMedium)
    }
}
