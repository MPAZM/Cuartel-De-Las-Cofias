package com.cuarteldelascofias.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftReportDao {
    @Query("SELECT * FROM shift_reports")
    fun observeReports(): Flow<List<ShiftReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(report: ShiftReportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reports: List<ShiftReportEntity>)

    @Query("SELECT COUNT(*) FROM shift_reports")
    suspend fun countReports(): Int
}
