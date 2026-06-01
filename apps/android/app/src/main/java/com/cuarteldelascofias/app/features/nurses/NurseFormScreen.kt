package com.cuarteldelascofias.app.features.nurses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cuarteldelascofias.app.R
import com.cuarteldelascofias.app.core.common.AppContainer
import com.cuarteldelascofias.app.core.common.AppCatalogs
import com.cuarteldelascofias.app.core.designsystem.AppFieldLabel
import com.cuarteldelascofias.app.core.designsystem.AppInfoCard
import com.cuarteldelascofias.app.core.designsystem.AppSelectField
import com.cuarteldelascofias.app.core.designsystem.AppSectionTitle
import com.cuarteldelascofias.app.core.designsystem.AppValidationSummary

@Composable
fun NurseFormScreen(
    onCancel: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NurseFormViewModel = viewModel(
        factory = NurseFormViewModel.factory(AppContainer.nurseRepository)
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
        AppInfoCard(
            title = stringResource(R.string.title_nueva_enfermera),
            body = stringResource(R.string.nurses_form_intro)
        )

        Text(
            text = stringResource(R.string.form_required_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        AppValidationSummary(
            errors = uiState.validationErrors.map { it.message }
        )

        AppSectionTitle(text = stringResource(R.string.nurses_form_section_general))

        OutlinedTextField(
            value = uiState.fullName,
            onValueChange = viewModel::onFullNameChange,
            label = { AppFieldLabel(text = stringResource(R.string.nurses_form_nombre), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.hasError(NurseField.FULL_NAME)
        )

        OutlinedTextField(
            value = uiState.phone,
            onValueChange = viewModel::onPhoneChange,
            label = { AppFieldLabel(text = stringResource(R.string.nurses_form_telefono), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = uiState.hasError(NurseField.PHONE)
        )

        OutlinedTextField(
            value = uiState.baseZone,
            onValueChange = viewModel::onBaseZoneChange,
            label = { AppFieldLabel(text = stringResource(R.string.nurses_form_zona), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.hasError(NurseField.BASE_ZONE)
        )

        AppSelectField(
            value = uiState.availabilityLabel,
            label = stringResource(R.string.nurses_form_disponibilidad),
            options = AppCatalogs.nurseAvailability.map { it.label },
            onOptionSelected = viewModel::onAvailabilityLabelChange,
            modifier = Modifier.fillMaxWidth(),
            required = true,
            isError = uiState.hasError(NurseField.AVAILABILITY_LABEL)
        )

        AppSectionTitle(text = stringResource(R.string.nurses_form_section_perfil))

        OutlinedTextField(
            value = uiState.specialtyNotes,
            onValueChange = viewModel::onSpecialtyNotesChange,
            label = { AppFieldLabel(text = stringResource(R.string.nurses_form_especialidad)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        AppSelectField(
            value = uiState.trustLevel,
            label = stringResource(R.string.nurses_form_confianza),
            options = AppCatalogs.nurseTrustLevels.map { it.label },
            onOptionSelected = viewModel::onTrustLevelChange,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.hasError(NurseField.TRUST_LEVEL)
        )

        OutlinedTextField(
            value = uiState.notes,
            onValueChange = viewModel::onNotesChange,
            label = { AppFieldLabel(text = stringResource(R.string.nurses_form_notas)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.nurses_form_activa))
            Switch(
                checked = uiState.active,
                onCheckedChange = viewModel::onActiveChange
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(R.string.accion_cancelar))
            }
            Button(
                onClick = {
                    if (viewModel.saveNurse()) {
                        onSaved()
                    }
                }
            ) {
                Text(text = stringResource(R.string.accion_guardar_enfermera))
            }
        }
    }
}
