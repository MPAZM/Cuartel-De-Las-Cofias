package com.cuarteldelascofias.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Query("SELECT * FROM patients ORDER BY active DESC, fullName ASC")
    fun observePatients(): Flow<List<PatientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(patient: PatientEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(patients: List<PatientEntity>)

    @Query("SELECT COUNT(*) FROM patients")
    suspend fun countPatients(): Int

    @Query("UPDATE patients SET activeShiftCount = :activeShiftCount WHERE id = :patientId")
    suspend fun updateActiveShiftCount(patientId: String, activeShiftCount: Int)
}
