package com.cuarteldelascofias.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shifts ORDER BY startLabel DESC")
    fun observeShifts(): Flow<List<ShiftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shift: ShiftEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shifts: List<ShiftEntity>)

    @Query("SELECT COUNT(*) FROM shifts")
    suspend fun countShifts(): Int

    @Query(
        """
        SELECT COUNT(*) FROM shifts
        WHERE patientId = :patientId
        AND status IN ('PENDING', 'SCHEDULED', 'IN_PROGRESS')
        """
    )
    suspend fun countActiveShiftsForPatient(patientId: String): Int
}
