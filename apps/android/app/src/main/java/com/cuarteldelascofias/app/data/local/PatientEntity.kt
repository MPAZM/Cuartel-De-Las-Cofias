package com.cuarteldelascofias.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val preferredName: String,
    val age: Int?,
    val careContext: String,
    val serviceLocationType: String,
    val serviceAddress: String,
    val emergencyContactName: String,
    val emergencyContactPhone: String,
    val active: Boolean,
    val notes: String,
    val activeShiftCount: Int
)
