package com.cuarteldelascofias.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shift_reports")
data class ShiftReportEntity(
    @PrimaryKey val id: String,
    val shiftId: String,
    val generalStatus: String,
    val medicationNotes: String,
    val vitalSignsNotes: String,
    val relevantEvents: String,
    val hospitalStaffComments: String,
    val familyComments: String,
    val handoffSummary: String,
    val createdAtLabel: String
)
