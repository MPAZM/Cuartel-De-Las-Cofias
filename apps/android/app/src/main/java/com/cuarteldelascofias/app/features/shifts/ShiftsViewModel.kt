package com.cuarteldelascofias.app.features.shifts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.core.navigation.AppDestination
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.model.Shift
import com.cuarteldelascofias.app.data.model.ShiftStatus
import com.cuarteldelascofias.app.data.repository.PatientRepository
import com.cuarteldelascofias.app.data.repository.ShiftRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class ShiftListItemUi(
    val id: String,
    val patientName: String,
    val nurseName: String,
    val scheduleSummary: String,
    val locationSummary: String,
    val status: ShiftStatus
)

data class ShiftsUiState(
    val shifts: List<ShiftListItemUi> = emptyList()
) {
    val activeShiftsCount: Int
        get() = shifts.count { it.status == ShiftStatus.IN_PROGRESS }
}

class ShiftsViewModel(
    shiftRepository: ShiftRepository,
    patientRepository: PatientRepository
) : ViewModel() {
    var uiState by mutableStateOf(ShiftsUiState())
        private set

    init {
        viewModelScope.launch {
            combine(
                shiftRepository.shifts,
                patientRepository.patients
            ) { shifts, patients ->
                ShiftsUiState(
                    shifts = shifts.map { shift ->
                        ShiftListItemUi(
                            id = shift.id,
                            patientName = patients.firstOrNull { it.id == shift.patientId }?.displayName
                                ?: "Paciente no disponible",
                            nurseName = shift.nurseName ?: "Pendiente de asignar",
                            scheduleSummary = shift.scheduleSummary,
                            locationSummary = shift.locationSummary,
                            status = shift.status
                        )
                    }
                )
            }.collect { state ->
                uiState = state
            }
        }
    }

    companion object {
        fun factory(
            shiftRepository: ShiftRepository,
            patientRepository: PatientRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShiftsViewModel(
                    shiftRepository = shiftRepository,
                    patientRepository = patientRepository
                )
            }
        }
    }
}

data class ShiftDetailUiState(
    val shift: Shift? = null,
    val patient: Patient? = null
)

class ShiftDetailViewModel(
    savedStateHandle: SavedStateHandle,
    shiftRepository: ShiftRepository,
    patientRepository: PatientRepository
) : ViewModel() {
    private val shiftId: String =
        checkNotNull(savedStateHandle[AppDestination.ShiftDetail.shiftIdArg])

    private val shift = shiftRepository.getShiftById(shiftId)

    val uiState = ShiftDetailUiState(
        shift = shift,
        patient = shift?.let { patientRepository.getPatientById(it.patientId) }
    )

    companion object {
        fun factory(
            shiftRepository: ShiftRepository,
            patientRepository: PatientRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ShiftDetailViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    shiftRepository = shiftRepository,
                    patientRepository = patientRepository
                )
            }
        }
    }
}
