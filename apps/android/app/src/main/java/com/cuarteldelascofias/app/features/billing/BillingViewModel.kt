package com.cuarteldelascofias.app.features.billing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cuarteldelascofias.app.data.repository.PatientRepository
import com.cuarteldelascofias.app.data.repository.ShiftRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

data class BillingItemUi(
    val id: String,
    val patientName: String,
    val shiftLabel: String,
    val rateAmount: String,
    val commissionAmount: String,
    val nurseNetAmount: String
)

data class BillingUiState(
    val totalCollected: String = "$0 MXN",
    val totalCommissions: String = "$0 MXN",
    val totalNurseNet: String = "$0 MXN",
    val items: List<BillingItemUi> = emptyList()
)

class BillingViewModel(
    shiftRepository: ShiftRepository,
    patientRepository: PatientRepository
) : ViewModel() {
    var uiState by mutableStateOf(BillingUiState())
        private set

    init {
        viewModelScope.launch {
            combine(
                shiftRepository.shifts,
                patientRepository.patients
            ) { shifts, patients ->
                BillingUiState(
                    totalCollected = formatCurrency(shifts.sumOf { parseAmount(it.rateAmount) }),
                    totalCommissions = formatCurrency(shifts.sumOf { parseAmount(it.commissionAmount) }),
                    totalNurseNet = formatCurrency(shifts.sumOf { parseAmount(it.nurseNetAmount) }),
                    items = shifts.map { shift ->
                        BillingItemUi(
                            id = shift.id,
                            patientName = patients.firstOrNull { it.id == shift.patientId }?.displayName
                                ?: "Paciente no disponible",
                            shiftLabel = "${shift.serviceType} - ${shift.startLabel}",
                            rateAmount = shift.rateAmount,
                            commissionAmount = shift.commissionAmount,
                            nurseNetAmount = shift.nurseNetAmount
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
                BillingViewModel(
                    shiftRepository = shiftRepository,
                    patientRepository = patientRepository
                )
            }
        }

        private val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(
            Locale.forLanguageTag("es-MX")
        ).apply {
            maximumFractionDigits = 0
        }

        private fun parseAmount(rawAmount: String): Int {
            val digits = rawAmount.filter { it.isDigit() }
            return digits.toIntOrNull() ?: 0
        }

        private fun formatCurrency(amount: Int): String {
            return "${currencyFormatter.format(amount)} MXN"
        }
    }
}
