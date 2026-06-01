package com.cuarteldelascofias.app.features.patients

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
import com.cuarteldelascofias.app.data.repository.PatientRepository
import kotlinx.coroutines.launch

data class PatientDetailUiState(
    val patient: Patient? = null
)

class PatientDetailViewModel(
    savedStateHandle: SavedStateHandle,
    patientRepository: PatientRepository
) : ViewModel() {
    private val patientId: String =
        checkNotNull(savedStateHandle[AppDestination.PatientDetail.patientIdArg])

    var uiState by mutableStateOf(PatientDetailUiState())
        private set

    init {
        viewModelScope.launch {
            patientRepository.patients.collect { patients ->
                uiState = PatientDetailUiState(
                    patient = patients.firstOrNull { it.id == patientId }
                )
            }
        }
    }

    companion object {
        fun factory(patientRepository: PatientRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PatientDetailViewModel(
                        savedStateHandle = createSavedStateHandle(),
                        patientRepository = patientRepository
                    )
                }
            }
    }
}
