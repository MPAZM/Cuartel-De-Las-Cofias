package com.cuarteldelascofias.app.data.model

enum class ShiftStatus {
    PENDING,
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

data class Shift(
    val id: String,
    val patientId: String,
    val nurseName: String?,
    val serviceType: String,
    val locationSummary: String,
    val startLabel: String,
    val endLabel: String,
    val scheduleSummary: String,
    val rateAmount: String,
    val commissionAmount: String,
    val nurseNetAmount: String,
    val status: ShiftStatus,
    val adminNotes: String
)
