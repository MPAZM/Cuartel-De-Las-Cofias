package com.cuarteldelascofias.app.data.model

data class Patient(
    val id: String,
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
) {
    val displayName: String
        get() = preferredName.ifBlank { fullName }

    val contactSummary: String
        get() = "$emergencyContactName · $emergencyContactPhone"

    val locationSummary: String
        get() = "$serviceLocationType · $serviceAddress"
}
