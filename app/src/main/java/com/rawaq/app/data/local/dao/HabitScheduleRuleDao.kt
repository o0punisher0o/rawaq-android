package com.rawaq.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rawaq.app.data.local.entity.HabitScheduleRuleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitScheduleRuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRule(rule: HabitScheduleRuleEntity)

    @Update
    suspend fun updateRule(rule: HabitScheduleRuleEntity)

    @Query("SELECT * FROM habit_schedule_rules WHERE habit_id = :habitId AND valid_until_epoch_day IS NULL LIMIT 1")
    suspend fun getActiveRuleForHabit(habitId: String): HabitScheduleRuleEntity?

    @Query("SELECT * FROM habit_schedule_rules WHERE habit_id = :habitId ORDER BY valid_from_epoch_day ASC")
    fun observeRulesForHabit(habitId: String): Flow<List<HabitScheduleRuleEntity>>

    @Query("""
        SELECT * FROM habit_schedule_rules 
        WHERE habit_id = :habitId 
          AND :epochDay >= valid_from_epoch_day 
          AND (valid_until_epoch_day IS NULL OR :epochDay <= valid_until_epoch_day)
        LIMIT 1
    """)
    suspend fun getRuleForHabitOnDate(habitId: String, epochDay: Int): HabitScheduleRuleEntity?

    @Query("SELECT * FROM habit_schedule_rules ORDER BY valid_from_epoch_day ASC")
    fun observeAllRules(): Flow<List<HabitScheduleRuleEntity>>
}
