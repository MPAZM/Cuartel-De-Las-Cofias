package com.cuarteldelascofias.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NurseDao {
    @Query("SELECT * FROM nurses ORDER BY active DESC, fullName ASC")
    fun observeNurses(): Flow<List<NurseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nurse: NurseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nurses: List<NurseEntity>)

    @Query("SELECT COUNT(*) FROM nurses")
    suspend fun countNurses(): Int
}
