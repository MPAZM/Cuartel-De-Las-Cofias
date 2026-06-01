package com.cuarteldelascofias.app.features.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.core.common.AppContainer
import com.cuarteldelascofias.app.core.designsystem.AppDetailRow
import com.cuarteldelascofias.app.core.designsystem.AppFieldLabel
import com.cuarteldelascofias.app.core.designsystem.AppInfoCard
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle
import com.cuarteldelascofias.app.core.designsystem.AppValidationSummary

@Composable
fun ShiftReportScreen(
    modifier: Modifier = Modifier,
    viewModel: ShiftReportViewModel = viewModel(
        factory = ShiftReportViewModel.factory(
            shiftRepository = AppContainer.shiftRepository,
            patientRepository = AppContainer.patientRepository,
            shiftReportRepository = AppContainer.shiftReportRepository
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
        if (uiState.shift == null) {
            Text(
                text = stringResource(R.string.report_not_found),
                style = MaterialTheme.typography.bodyLarge
            )
            return@Column
        }

        AppInfoCard(
            title = uiState.patient?.displayName ?: stringResource(R.string.shift_label_paciente),
            body = stringResource(R.string.report_intro)
        )

        AppDetailRow(
            label = stringResource(R.string.report_label_guardia),
            value = uiState.shift.scheduleSummary
        )
        AppDetailRow(
            label = stringResource(R.string.report_label_elaborado),
            value = uiState.report?.createdAtLabel ?: stringResource(R.string.report_label_pendiente)
        )

        Text(
            text = stringResource(R.string.form_required_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        AppValidationSummary(errors = uiState.validationErrors.map { it.message })

        if (uiState.saveSucceeded) {
            Text(
                text = stringResource(R.string.report_saved),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        AppSectionTitle(text = stringResource(R.string.report_label_estado_general), required = true)
        OutlinedTextField(
            value = uiState.generalStatus,
            onValueChange = viewModel::onGeneralStatusChange,
            label = {
                AppFieldLabel(
                    text = stringResource(R.string.report_label_estado_general),
                    required = true
                )
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.GENERAL_STATUS)
        )

        AppSectionTitle(text = stringResource(R.string.report_label_medicamentos))
        OutlinedTextField(
            value = uiState.medicationNotes,
            onValueChange = viewModel::onMedicationNotesChange,
            label = { AppFieldLabel(text = stringResource(R.string.report_label_medicamentos)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.MEDICATION_NOTES)
        )

        AppSectionTitle(text = stringResource(R.string.report_label_signos))
        OutlinedTextField(
            value = uiState.vitalSignsNotes,
            onValueChange = viewModel::onVitalSignsNotesChange,
            label = { AppFieldLabel(text = stringResource(R.string.report_label_signos)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.VITAL_SIGNS_NOTES)
        )

        AppSectionTitle(text = stringResource(R.string.report_label_eventos))
        OutlinedTextField(
            value = uiState.relevantEvents,
            onValueChange = viewModel::onRelevantEventsChange,
            label = { AppFieldLabel(text = stringResource(R.string.report_label_eventos)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.RELEVANT_EVENTS)
        )

        AppSectionTitle(text = stringResource(R.string.report_label_comentarios_hospital))
        OutlinedTextField(
            value = uiState.hospitalStaffComments,
            onValueChange = viewModel::onHospitalStaffCommentsChange,
            label = { AppFieldLabel(text = stringResource(R.string.report_label_comentarios_hospital)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.HOSPITAL_STAFF_COMMENTS)
        )

        AppSectionTitle(text = stringResource(R.string.report_label_comentarios_familia))
        OutlinedTextField(
            value = uiState.familyComments,
            onValueChange = viewModel::onFamilyCommentsChange,
            label = { AppFieldLabel(text = stringResource(R.string.report_label_comentarios_familia)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.FAMILY_COMMENTS)
        )

        AppSectionTitle(text = stringResource(R.string.report_label_entrega), required = true)
        OutlinedTextField(
            value = uiState.handoffSummary,
            onValueChange = viewModel::onHandoffSummaryChange,
            label = {
                AppFieldLabel(
                    text = stringResource(R.string.report_label_entrega),
                    required = true
                )
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            isError = uiState.hasError(ShiftReportField.HANDOFF_SUMMARY)
        )

        Button(
            onClick = { viewModel.saveReport() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.accion_guardar_reporte))
        }
    }
}
