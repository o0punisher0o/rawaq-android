package com.rawaq.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rawaq.app.data.local.entity.HabitCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCompletionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCompletion(completion: HabitCompletionEntity)

    @Query("SELECT * FROM habit_completions WHERE habit_id = :habitId AND logical_date = :logicalDate LIMIT 1")
    suspend fun getCompletion(habitId: String, logicalDate: Int): HabitCompletionEntity?

    @Query("SELECT * FROM habit_completions WHERE logical_date = :logicalDate")
    fun observeCompletionsForDate(logicalDate: Int): Flow<List<HabitCompletionEntity>>

    @Query("SELECT * FROM habit_completions WHERE habit_id = :habitId AND logical_date BETWEEN :startEpochDay AND :endEpochDay ORDER BY logical_date ASC")
    fun observeCompletionsForRange(habitId: String, startEpochDay: Int, endEpochDay: Int): Flow<List<HabitCompletionEntity>>

    @Query("SELECT * FROM habit_completions WHERE habit_id = :habitId ORDER BY logical_date ASC")
    fun observeAllCompletionsForHabit(habitId: String): Flow<List<HabitCompletionEntity>>

    @Query("DELETE FROM habit_completions WHERE habit_id = :habitId AND logical_date = :logicalDate")
    suspend fun deleteCompletion(habitId: String, logicalDate: Int)
}
