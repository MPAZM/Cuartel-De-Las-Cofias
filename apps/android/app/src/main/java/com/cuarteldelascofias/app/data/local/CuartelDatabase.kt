package com.cuarteldelascofias.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PatientEntity::class, NurseEntity::class, ShiftEntity::class, ShiftReportEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ShiftStatusConverters::class)
abstract class CuartelDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun nurseDao(): NurseDao
    abstract fun shiftDao(): ShiftDao
    abstract fun shiftReportDao(): ShiftReportDao

    companion object {
        suspend fun seedIfEmpty(database: CuartelDatabase) {
            if (database.patientDao().countPatients() == 0) {
                database.patientDao().insertAll(SeedData.patients)
            }
            if (database.nurseDao().countNurses() == 0) {
                database.nurseDao().insertAll(SeedData.nurses)
            }
            if (database.shiftDao().countShifts() == 0) {
                database.shiftDao().insertAll(SeedData.shifts)
            }
            if (database.shiftReportDao().countReports() == 0) {
                database.shiftReportDao().insertAll(SeedData.reports)
            }
        }
    }
}
