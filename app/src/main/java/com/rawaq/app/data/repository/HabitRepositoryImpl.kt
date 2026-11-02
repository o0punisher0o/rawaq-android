package com.rawaq.app.data.repository

import com.rawaq.app.data.local.dao.HabitCompletionDao
import com.rawaq.app.data.local.dao.HabitDao
import com.rawaq.app.data.local.dao.HabitScheduleRuleDao
import com.rawaq.app.data.local.mapper.toDomain
import com.rawaq.app.data.local.mapper.toEntity
import com.rawaq.app.domain.model.CompletionStatus
import com.rawaq.app.domain.model.DailyHabitStatus
import com.rawaq.app.domain.model.Habit
import com.rawaq.app.domain.model.HabitCompletion
import com.rawaq.app.domain.model.HabitScheduleRule
import com.rawaq.app.domain.model.ScheduleType
import com.rawaq.app.domain.model.TargetType
import com.rawaq.app.domain.repository.HabitRepository
import com.rawaq.app.domain.util.ScheduleEvaluator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitScheduleRuleDao: HabitScheduleRuleDao,
    private val habitCompletionDao: HabitCompletionDao
) : HabitRepository {

    override suspend fun createHabit(habit: Habit, initialSchedule: HabitScheduleRule) {
        habitDao.insertHabit(habit.toEntity())
        habitScheduleRuleDao.insertRule(initialSchedule.toEntity())
    }

    override suspend fun updateHabitMetadata(habit: Habit) {
        habitDao.updateHabit(habit.toEntity().copy(updatedAtTimestamp = System.currentTimeMillis()))
    }

    override suspend fun archiveHabit(habitId: String, currentLogicalEpochDay: Int) {
        habitDao.archiveHabit(
            habitId = habitId,
            archivedAtEpochDay = currentLogicalEpochDay,
            updatedAtTimestamp = System.currentTimeMillis()
        )
    }

    override suspend fun pauseHabit(habitId: String, currentLogicalEpochDay: Int) {
        val pausedRule = HabitScheduleRule(
            id = UUID.randomUUID().toString(),
            habitId = habitId,
            scheduleType = ScheduleType.PAUSED,
            daysOfWeekMask = null,
            validFromEpochDay = currentLogicalEpochDay,
            validUntilEpochDay = null
        )
        updateSchedule(habitId, currentLogicalEpochDay, pausedRule)
    }

    override suspend fun resumeHabit(habitId: String, currentLogicalEpochDay: Int, schedule: HabitScheduleRule) {
        updateSchedule(habitId, currentLogicalEpochDay, schedule)
    }

    override suspend fun updateSchedule(
        habitId: String,
        currentLogicalEpochDay: Int,
        newSchedule: HabitScheduleRule
    ) {
        val activeRuleEntity = habitScheduleRuleDao.getActiveRuleForHabit(habitId)

        if (activeRuleEntity != null) {
            if (activeRuleEntity.validFromEpochDay == currentLogicalEpochDay) {
                // Changing schedule on the day it was created/updated: replace existing rule
                habitScheduleRuleDao.insertRule(
                    newSchedule.toEntity().copy(
                        id = activeRuleEntity.id,
                        validFromEpochDay = currentLogicalEpochDay,
                        validUntilEpochDay = null
                    )
                )
                return
            } else if (activeRuleEntity.validFromEpochDay < currentLogicalEpochDay) {
                // Close previous rule version at N - 1
                habitScheduleRuleDao.updateRule(
                    activeRuleEntity.copy(validUntilEpochDay = currentLogicalEpochDay - 1)
                )
            }
        }

        // Insert new rule starting from currentLogicalEpochDay
        habitScheduleRuleDao.insertRule(
            newSchedule.toEntity().copy(
                validFromEpochDay = currentLogicalEpochDay,
                validUntilEpochDay = null
            )
        )
    }

    override fun observeDailyHabitStatuses(logicalEpochDay: Int): Flow<List<DailyHabitStatus>> {
        return combine(
            habitDao.observeAllHabits(),
            habitScheduleRuleDao.observeAllRules(),
            habitCompletionDao.observeCompletionsForDate(logicalEpochDay)
        ) { habitEntities, ruleEntities, completionEntities ->
            val completionsMap = completionEntities.associateBy { it.habitId }
            val rulesByHabit = ruleEntities.groupBy { it.habitId }

            habitEntities.mapNotNull { habitEntity ->
                // Exclude if habit was archived ON or BEFORE logicalEpochDay
                if (habitEntity.isArchived && habitEntity.archivedAtEpochDay != null && logicalEpochDay >= habitEntity.archivedAtEpochDay) {
                    return@mapNotNull null
                }

                val habitRules = rulesByHabit[habitEntity.id] ?: emptyList()
                val activeRuleEntity = habitRules.find { rule ->
                    logicalEpochDay >= rule.validFromEpochDay &&
                            (rule.validUntilEpochDay == null || logicalEpochDay <= rule.validUntilEpochDay)
                } ?: return@mapNotNull null

                if (activeRuleEntity.scheduleType == ScheduleType.PAUSED.name) {
                    return@mapNotNull null
                }

                val habit = habitEntity.toDomain()
                val activeRule = activeRuleEntity.toDomain()
                val isDue = ScheduleEvaluator.isDue(activeRule, logicalEpochDay)
                val completion = completionsMap[habitEntity.id]?.toDomain()

                DailyHabitStatus(
                    habit = habit,
                    activeScheduleRule = activeRule,
                    isDue = isDue,
                    completion = completion
                )
            }
        }
    }

    override suspend fun setCompletion(
        habitId: String,
        logicalEpochDay: Int,
        status: CompletionStatus,
        numericValue: Double?,
        note: String?
    ) {
        val habitEntity = habitDao.getHabitById(habitId) ?: return

        require(numericValue == null || numericValue >= 0.0) { "numericValue must not be negative" }

        val finalStatus = if (habitEntity.targetType == TargetType.NUMERIC.name) {
            when (status) {
                CompletionStatus.SKIPPED, CompletionStatus.CANCELLED -> status
                else -> {
                    val value = numericValue ?: 0.0
                    if (value >= habitEntity.targetValue) CompletionStatus.COMPLETED else CompletionStatus.PARTIAL
                }
            }
        } else {
            status
        }

        val existingCompletion = habitCompletionDao.getCompletion(habitId, logicalEpochDay)
        val completionId = existingCompletion?.id ?: UUID.randomUUID().toString()
        val now = System.currentTimeMillis()

        val completionEntity = com.rawaq.app.data.local.entity.HabitCompletionEntity(
            id = completionId,
            habitId = habitId,
            logicalDate = logicalEpochDay,
            status = finalStatus.name,
            numericValue = numericValue,
            completedAtTimestamp = existingCompletion?.completedAtTimestamp ?: now,
            note = note ?: existingCompletion?.note,
            updatedAtTimestamp = now
        )

        habitCompletionDao.upsertCompletion(completionEntity)
    }

    override suspend fun removeCompletion(habitId: String, logicalEpochDay: Int) {
        habitCompletionDao.deleteCompletion(habitId, logicalEpochDay)
    }

    override fun observeCompletionsForHabit(habitId: String): Flow<List<HabitCompletion>> {
        return habitCompletionDao.observeAllCompletionsForHabit(habitId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun observeCompletionsForRange(
        habitId: String,
        startEpochDay: Int,
        endEpochDay: Int
    ): Flow<List<HabitCompletion>> {
        return habitCompletionDao.observeCompletionsForRange(habitId, startEpochDay, endEpochDay).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun observeAllHabits(): Flow<List<Habit>> {
        return habitDao.observeAllHabits().map { list ->
            list.map { it.toDomain() }
        }
    }
}
