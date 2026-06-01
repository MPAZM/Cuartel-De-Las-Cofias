package com.cuarteldelascofias.app.data.model

data class ShiftReport(
    val id: String,
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
