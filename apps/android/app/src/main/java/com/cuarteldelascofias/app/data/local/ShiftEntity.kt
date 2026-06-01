package com.cuarteldelascofias.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cuarteldelascofias.app.data.model.ShiftStatus

@Entity(tableName = "shifts")
data class ShiftEntity(
    @PrimaryKey val id: String,
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
