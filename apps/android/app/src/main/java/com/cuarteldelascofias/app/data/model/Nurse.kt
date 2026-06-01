package com.cuarteldelascofias.app.data.model

data class Nurse(
    val id: String,
    val fullName: String,
    val phone: String,
    val specialtyNotes: String,
    val baseZone: String,
    val availabilityLabel: String,
    val active: Boolean,
    val trustLevel: String,
    val notes: String
)
