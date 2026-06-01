package com.cuarteldelascofias.app.features.nurses

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
import com.cuarteldelascofias.app.data.model.Nurse
import com.cuarteldelascofias.app.data.repository.NurseRepository
import kotlinx.coroutines.launch

data class NursesUiState(
    val nurses: List<Nurse> = emptyList()
) {
    val activeNursesCount: Int
        get() = nurses.count { it.active }
}

data class NurseDetailUiState(
    val nurse: Nurse? = null
)

class NursesViewModel(
    nurseRepository: NurseRepository
) : ViewModel() {
    var uiState by mutableStateOf(
        NursesUiState(nurses = nurseRepository.nurses.value)
    )
        private set

    init {
        viewModelScope.launch {
            nurseRepository.nurses.collect { nurses ->
                uiState = NursesUiState(nurses = nurses)
            }
        }
    }

    companion object {
        fun factory(nurseRepository: NurseRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    NursesViewModel(nurseRepository)
                }
            }
    }
}

class NurseDetailViewModel(
    savedStateHandle: SavedStateHandle,
    nurseRepository: NurseRepository
) : ViewModel() {
    private val nurseId: String =
        checkNotNull(savedStateHandle[AppDestination.NurseDetail.nurseIdArg])

    val uiState = NurseDetailUiState(
        nurse = nurseRepository.getNurseById(nurseId)
    )

    companion object {
        fun factory(nurseRepository: NurseRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    NurseDetailViewModel(
                        savedStateHandle = createSavedStateHandle(),
                        nurseRepository = nurseRepository
                    )
                }
            }
    }
}
