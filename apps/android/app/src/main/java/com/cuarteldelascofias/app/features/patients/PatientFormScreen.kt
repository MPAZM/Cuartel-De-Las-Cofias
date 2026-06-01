package com.cuarteldelascofias.app.features.patients

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
fun PatientFormScreen(
    onCancel: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PatientFormViewModel = viewModel(
        factory = PatientFormViewModel.factory(AppContainer.patientRepository)
    )
) {
    val uiState = viewModel.uiState
    val screenTitle = if (uiState.isEditMode) {
        stringResource(R.string.title_editar_paciente)
    } else {
        stringResource(R.string.title_nuevo_paciente)
    }
    val submitLabel = if (uiState.isEditMode) {
        stringResource(R.string.accion_actualizar_paciente)
    } else {
        stringResource(R.string.accion_guardar_paciente)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (uiState.recordNotFound) {
            AppInfoCard(
                title = screenTitle,
                body = stringResource(R.string.patient_edit_not_found)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = stringResource(R.string.accion_cancelar))
                }
            }
            return@Column
        }

        AppInfoCard(
            title = screenTitle,
            body = stringResource(R.string.patients_form_intro)
        )

        Text(
            text = stringResource(R.string.form_required_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        AppValidationSummary(
            errors = uiState.validationErrors.map { it.message }
        )

        AppSectionTitle(text = stringResource(R.string.patients_form_section_general))

        OutlinedTextField(
            value = uiState.fullName,
            onValueChange = viewModel::onFullNameChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_nombre_completo), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.hasError(PatientField.FULL_NAME)
        )

        OutlinedTextField(
            value = uiState.preferredName,
            onValueChange = viewModel::onPreferredNameChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_nombre_preferido)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.age,
            onValueChange = viewModel::onAgeChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_edad)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = uiState.hasError(PatientField.AGE)
        )

        OutlinedTextField(
            value = uiState.careContext,
            onValueChange = viewModel::onCareContextChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_contexto)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        AppSectionTitle(text = stringResource(R.string.patients_form_section_ubicacion))

        AppSelectField(
            value = uiState.serviceLocationType,
            label = stringResource(R.string.patients_form_tipo_ubicacion),
            options = AppCatalogs.patientLocationTypes.map { it.label },
            onOptionSelected = viewModel::onServiceLocationTypeChange,
            modifier = Modifier.fillMaxWidth(),
            required = true,
            isError = uiState.hasError(PatientField.SERVICE_LOCATION_TYPE)
        )

        OutlinedTextField(
            value = uiState.serviceAddress,
            onValueChange = viewModel::onServiceAddressChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_direccion), required = true) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            isError = uiState.hasError(PatientField.SERVICE_ADDRESS)
        )

        AppSectionTitle(text = stringResource(R.string.patients_form_section_contacto))

        OutlinedTextField(
            value = uiState.emergencyContactName,
            onValueChange = viewModel::onEmergencyContactNameChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_contacto), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.hasError(PatientField.EMERGENCY_CONTACT_NAME)
        )

        OutlinedTextField(
            value = uiState.emergencyContactPhone,
            onValueChange = viewModel::onEmergencyContactPhoneChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_telefono), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = uiState.hasError(PatientField.EMERGENCY_CONTACT_PHONE)
        )

        AppSectionTitle(text = stringResource(R.string.patients_form_section_notas))

        OutlinedTextField(
            value = uiState.notes,
            onValueChange = viewModel::onNotesChange,
            label = { AppFieldLabel(text = stringResource(R.string.patients_form_notas)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.patients_form_activo))
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
                    if (viewModel.savePatient()) {
                        onSaved()
                    }
                }
            ) {
                Text(text = submitLabel)
            }
        }
    }
}
