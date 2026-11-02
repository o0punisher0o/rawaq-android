package com.rawaq.app.domain.repository

import com.rawaq.app.domain.model.CompletionStatus
import com.rawaq.app.domain.model.DailyHabitStatus
import com.rawaq.app.domain.model.Habit
import com.rawaq.app.domain.model.HabitCompletion
import com.rawaq.app.domain.model.HabitScheduleRule
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun createHabit(habit: Habit, initialSchedule: HabitScheduleRule)
    suspend fun updateHabitMetadata(habit: Habit)
    suspend fun archiveHabit(habitId: String, currentLogicalEpochDay: Int)
    suspend fun pauseHabit(habitId: String, currentLogicalEpochDay: Int)
    suspend fun resumeHabit(habitId: String, currentLogicalEpochDay: Int, schedule: HabitScheduleRule)
    suspend fun updateSchedule(habitId: String, currentLogicalEpochDay: Int, newSchedule: HabitScheduleRule)
    fun observeDailyHabitStatuses(logicalEpochDay: Int): Flow<List<DailyHabitStatus>>
    suspend fun setCompletion(
        habitId: String,
        logicalEpochDay: Int,
        status: CompletionStatus,
        numericValue: Double? = null,
        note: String? = null
    )
    suspend fun removeCompletion(habitId: String, logicalEpochDay: Int)
    fun observeCompletionsForHabit(habitId: String): Flow<List<HabitCompletion>>
    fun observeCompletionsForRange(habitId: String, startEpochDay: Int, endEpochDay: Int): Flow<List<HabitCompletion>>
    fun observeAllHabits(): Flow<List<Habit>>
}
