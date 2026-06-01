package com.cuarteldelascofias.app.features.patients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.repository.PatientRepository
import kotlinx.coroutines.launch

data class PatientsUiState(
    val patients: List<Patient> = emptyList()
) {
    val activePatientsCount: Int
        get() = patients.count { it.active }
}

class PatientsViewModel(
    private val patientRepository: PatientRepository
) : ViewModel() {
    var uiState by mutableStateOf(
        PatientsUiState(patients = patientRepository.patients.value)
    )
        private set

    init {
        viewModelScope.launch {
            patientRepository.patients.collect { patients ->
                uiState = PatientsUiState(patients = patients)
            }
        }
    }

    companion object {
        fun factory(patientRepository: PatientRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    PatientsViewModel(patientRepository)
                }
            }
    }
}
