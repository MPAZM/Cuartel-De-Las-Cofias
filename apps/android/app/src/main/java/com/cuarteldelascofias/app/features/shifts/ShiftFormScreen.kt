package com.cuarteldelascofias.app.features.shifts

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.cuarteldelascofias.app.data.model.ShiftStatus
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftFormScreen(
    onCancel: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShiftFormViewModel = viewModel(
        factory = ShiftFormViewModel.factory(
            patientRepository = AppContainer.patientRepository,
            nurseRepository = AppContainer.nurseRepository,
            shiftRepository = AppContainer.shiftRepository
        )
    )
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState
    var patientExpanded by remember { mutableStateOf(false) }
    var nurseExpanded by remember { mutableStateOf(false) }

    fun openDateTimePicker(initialValue: Long?, onSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = initialValue ?: System.currentTimeMillis()
        }

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        calendar.set(Calendar.SECOND, 0)
                        calendar.set(Calendar.MILLISECOND, 0)
                        onSelected(calendar.timeInMillis)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppInfoCard(
            title = stringResource(R.string.title_nueva_guardia),
            body = stringResource(R.string.shifts_form_intro)
        )

        Text(
            text = stringResource(R.string.form_required_hint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        AppValidationSummary(
            errors = uiState.validationErrors.map { it.message }
        )

        AppSectionTitle(text = stringResource(R.string.shifts_form_section_paciente), required = true)

        ExposedDropdownMenuBox(
            expanded = patientExpanded,
            onExpandedChange = { patientExpanded = !patientExpanded }
        ) {
            OutlinedTextField(
                value = uiState.patientQuery,
                onValueChange = {
                    viewModel.onPatientQueryChange(it)
                    patientExpanded = true
                },
                label = {
                    AppFieldLabel(
                        text = stringResource(R.string.shifts_form_search_patient),
                        required = true
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = patientExpanded)
                },
                isError = uiState.hasError(ShiftField.PATIENT)
            )

            ExposedDropdownMenu(
                expanded = patientExpanded,
                onDismissRequest = { patientExpanded = false }
            ) {
                uiState.filteredPatients.forEach { patient ->
                    DropdownMenuItem(
                        text = {
                            Column {
                                Text(text = patient.displayName)
                                Text(
                                    text = patient.locationSummary,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        onClick = {
                            viewModel.onPatientSelected(patient.id)
                            patientExpanded = false
                        }
                    )
                }
            }
        }

        AppSectionTitle(text = stringResource(R.string.shifts_form_section_enfermera))

        ExposedDropdownMenuBox(
            expanded = nurseExpanded,
            onExpandedChange = { nurseExpanded = !nurseExpanded }
        ) {
            OutlinedTextField(
                value = uiState.nurseQuery,
                onValueChange = {
                    viewModel.onNurseQueryChange(it)
                    nurseExpanded = true
                },
                label = { AppFieldLabel(text = stringResource(R.string.shifts_form_search_nurse)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = nurseExpanded)
                }
            )

            ExposedDropdownMenu(
                expanded = nurseExpanded,
                onDismissRequest = { nurseExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.shift_pending_assignment)) },
                    onClick = {
                        viewModel.onNurseSelected(null)
                        nurseExpanded = false
                    }
                )
                uiState.filteredNurses.forEach { nurseName ->
                    DropdownMenuItem(
                        text = { Text(text = nurseName) },
                        onClick = {
                            viewModel.onNurseSelected(nurseName)
                            nurseExpanded = false
                        }
                    )
                }
            }
        }

        AppSectionTitle(text = stringResource(R.string.shifts_form_section_datos))

        AppSelectField(
            value = uiState.serviceType,
            label = stringResource(R.string.shifts_form_tipo_servicio),
            options = AppCatalogs.shiftServiceTypes.map { it.label },
            onOptionSelected = viewModel::onServiceTypeChange,
            modifier = Modifier.fillMaxWidth(),
            required = true,
            isError = uiState.hasError(ShiftField.SERVICE_TYPE)
        )

        OutlinedTextField(
            value = uiState.locationSummary,
            onValueChange = viewModel::onLocationSummaryChange,
            label = { AppFieldLabel(text = stringResource(R.string.shifts_form_ubicacion), required = true) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2,
            isError = uiState.hasError(ShiftField.LOCATION)
        )

        OutlinedTextField(
            value = viewModel.formattedDateTime(uiState.startDateTimeMillis),
            onValueChange = {},
            label = { AppFieldLabel(text = stringResource(R.string.shifts_form_inicio), required = true) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            isError = uiState.hasError(ShiftField.START)
        )

        TextButton(
            onClick = {
                openDateTimePicker(uiState.startDateTimeMillis, viewModel::onStartDateTimeSelected)
            }
        ) {
            Text(text = stringResource(R.string.shifts_form_pick_start))
        }

        OutlinedTextField(
            value = viewModel.formattedDateTime(uiState.endDateTimeMillis),
            onValueChange = {},
            label = { AppFieldLabel(text = stringResource(R.string.shifts_form_fin), required = true) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            isError = uiState.hasError(ShiftField.END)
        )

        TextButton(
            onClick = {
                openDateTimePicker(uiState.endDateTimeMillis, viewModel::onEndDateTimeSelected)
            }
        ) {
            Text(text = stringResource(R.string.shifts_form_pick_end))
        }

        AppSectionTitle(text = stringResource(R.string.shifts_form_section_finanzas))

        OutlinedTextField(
            value = uiState.rateAmount,
            onValueChange = viewModel::onRateAmountChange,
            label = { AppFieldLabel(text = stringResource(R.string.shifts_form_tarifa), required = true) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = uiState.hasError(ShiftField.RATE)
        )

        OutlinedTextField(
            value = uiState.commissionAmount,
            onValueChange = viewModel::onCommissionAmountChange,
            label = { AppFieldLabel(text = stringResource(R.string.shifts_form_comision)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = uiState.hasError(ShiftField.COMMISSION)
        )

        AppSectionTitle(text = stringResource(R.string.shifts_form_section_estado))

        ShiftStatus.entries.forEach { status ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = uiState.status == status,
                        onClick = { viewModel.onStatusSelected(status) }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = uiState.status == status,
                    onClick = { viewModel.onStatusSelected(status) }
                )
                Text(text = status.label())
            }
        }

        OutlinedTextField(
            value = uiState.adminNotes,
            onValueChange = viewModel::onAdminNotesChange,
            label = { AppFieldLabel(text = stringResource(R.string.shifts_form_notas)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(R.string.accion_cancelar))
            }
            Button(
                onClick = {
                    if (viewModel.saveShift()) {
                        onSaved()
                    }
                }
            ) {
                Text(text = stringResource(R.string.accion_guardar_guardia))
            }
        }
    }
}
