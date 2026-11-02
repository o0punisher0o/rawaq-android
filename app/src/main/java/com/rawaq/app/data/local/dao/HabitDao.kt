package com.rawaq.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rawaq.app.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: String): HabitEntity?

    @Query("SELECT * FROM habits WHERE is_archived = 0 ORDER BY sort_order ASC")
    fun observeActiveHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits ORDER BY sort_order ASC")
    fun observeAllHabits(): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET is_archived = 1, archived_at_epoch_day = :archivedAtEpochDay, updated_at_timestamp = :updatedAtTimestamp WHERE id = :habitId")
    suspend fun archiveHabit(habitId: String, archivedAtEpochDay: Int, updatedAtTimestamp: Long)
}
