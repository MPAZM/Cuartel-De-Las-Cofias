package com.cuarteldelascofias.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nurses")
data class NurseEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val phone: String,
    val specialtyNotes: String,
    val baseZone: String,
    val availabilityLabel: String,
    val active: Boolean,
    val trustLevel: String,
    val notes: String
)
