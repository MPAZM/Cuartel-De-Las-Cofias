package com.cuarteldelascofias.app.data.local

import com.cuarteldelascofias.app.data.model.Nurse
import com.cuarteldelascofias.app.data.model.Patient
import com.cuarteldelascofias.app.data.model.Shift
import com.cuarteldelascofias.app.data.model.ShiftReport

fun PatientEntity.toModel(): Patient = Patient(
    id = id,
    fullName = fullName,
    preferredName = preferredName,
    age = age,
    careContext = careContext,
    serviceLocationType = serviceLocationType,
    serviceAddress = serviceAddress,
    emergencyContactName = emergencyContactName,
    emergencyContactPhone = emergencyContactPhone,
    active = active,
    notes = notes,
    activeShiftCount = activeShiftCount
)

fun Patient.toEntity(): PatientEntity = PatientEntity(
    id = id,
    fullName = fullName,
    preferredName = preferredName,
    age = age,
    careContext = careContext,
    serviceLocationType = serviceLocationType,
    serviceAddress = serviceAddress,
    emergencyContactName = emergencyContactName,
    emergencyContactPhone = emergencyContactPhone,
    active = active,
    notes = notes,
    activeShiftCount = activeShiftCount
)

fun NurseEntity.toModel(): Nurse = Nurse(
    id = id,
    fullName = fullName,
    phone = phone,
    specialtyNotes = specialtyNotes,
    baseZone = baseZone,
    availabilityLabel = availabilityLabel,
    active = active,
    trustLevel = trustLevel,
    notes = notes
)

fun Nurse.toEntity(): NurseEntity = NurseEntity(
    id = id,
    fullName = fullName,
    phone = phone,
    specialtyNotes = specialtyNotes,
    baseZone = baseZone,
    availabilityLabel = availabilityLabel,
    active = active,
    trustLevel = trustLevel,
    notes = notes
)

fun ShiftEntity.toModel(): Shift = Shift(
    id = id,
    patientId = patientId,
    nurseName = nurseName,
    serviceType = serviceType,
    locationSummary = locationSummary,
    startLabel = startLabel,
    endLabel = endLabel,
    scheduleSummary = scheduleSummary,
    rateAmount = rateAmount,
    commissionAmount = commissionAmount,
    nurseNetAmount = nurseNetAmount,
    status = status,
    adminNotes = adminNotes
)

fun Shift.toEntity(): ShiftEntity = ShiftEntity(
    id = id,
    patientId = patientId,
    nurseName = nurseName,
    serviceType = serviceType,
    locationSummary = locationSummary,
    startLabel = startLabel,
    endLabel = endLabel,
    scheduleSummary = scheduleSummary,
    rateAmount = rateAmount,
    commissionAmount = commissionAmount,
    nurseNetAmount = nurseNetAmount,
    status = status,
    adminNotes = adminNotes
)

fun ShiftReportEntity.toModel(): ShiftReport = ShiftReport(
    id = id,
    shiftId = shiftId,
    generalStatus = generalStatus,
    medicationNotes = medicationNotes,
    vitalSignsNotes = vitalSignsNotes,
    relevantEvents = relevantEvents,
    hospitalStaffComments = hospitalStaffComments,
    familyComments = familyComments,
    handoffSummary = handoffSummary,
    createdAtLabel = createdAtLabel
)

fun ShiftReport.toEntity(): ShiftReportEntity = ShiftReportEntity(
    id = id,
    shiftId = shiftId,
    generalStatus = generalStatus,
    medicationNotes = medicationNotes,
    vitalSignsNotes = vitalSignsNotes,
    relevantEvents = relevantEvents,
    hospitalStaffComments = hospitalStaffComments,
    familyComments = familyComments,
    handoffSummary = handoffSummary,
    createdAtLabel = createdAtLabel
)
