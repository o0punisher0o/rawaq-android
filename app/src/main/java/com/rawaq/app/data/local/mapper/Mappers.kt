package com.rawaq.app.data.local.mapper

import com.rawaq.app.data.local.entity.GoalEntity
import com.rawaq.app.data.local.entity.HabitCompletionEntity
import com.rawaq.app.data.local.entity.HabitEntity
import com.rawaq.app.data.local.entity.HabitScheduleRuleEntity
import com.rawaq.app.domain.model.CompletionStatus
import com.rawaq.app.domain.model.Goal
import com.rawaq.app.domain.model.GoalStatus
import com.rawaq.app.domain.model.Habit
import com.rawaq.app.domain.model.HabitCompletion
import com.rawaq.app.domain.model.HabitScheduleRule
import com.rawaq.app.domain.model.ScheduleType
import com.rawaq.app.domain.model.TargetType

fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = id,
        title = title,
        description = description,
        category = category,
        targetType = TargetType.valueOf(targetType),
        targetValue = targetValue,
        unit = unit,
        colorHex = colorHex,
        iconName = iconName,
        isArchived = isArchived,
        archivedAtEpochDay = archivedAtEpochDay,
        excludeFromAnalytics = excludeFromAnalytics,
        sortOrder = sortOrder,
        createdAtTimestamp = createdAtTimestamp,
        updatedAtTimestamp = updatedAtTimestamp
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        targetType = targetType.name,
        targetValue = targetValue,
        unit = unit,
        colorHex = colorHex,
        iconName = iconName,
        isArchived = isArchived,
        archivedAtEpochDay = archivedAtEpochDay,
        excludeFromAnalytics = excludeFromAnalytics,
        sortOrder = sortOrder,
        createdAtTimestamp = createdAtTimestamp,
        updatedAtTimestamp = updatedAtTimestamp
    )
}

fun HabitScheduleRuleEntity.toDomain(): HabitScheduleRule {
    return HabitScheduleRule(
        id = id,
        habitId = habitId,
        scheduleType = ScheduleType.valueOf(scheduleType),
        daysOfWeekMask = daysOfWeekMask,
        validFromEpochDay = validFromEpochDay,
        validUntilEpochDay = validUntilEpochDay,
        createdAtTimestamp = createdAtTimestamp
    )
}

fun HabitScheduleRule.toEntity(): HabitScheduleRuleEntity {
    return HabitScheduleRuleEntity(
        id = id,
        habitId = habitId,
        scheduleType = scheduleType.name,
        daysOfWeekMask = daysOfWeekMask,
        validFromEpochDay = validFromEpochDay,
        validUntilEpochDay = validUntilEpochDay,
        createdAtTimestamp = createdAtTimestamp
    )
}

fun HabitCompletionEntity.toDomain(): HabitCompletion {
    return HabitCompletion(
        id = id,
        habitId = habitId,
        logicalDate = logicalDate,
        status = CompletionStatus.valueOf(status),
        numericValue = numericValue,
        completedAtTimestamp = completedAtTimestamp,
        note = note,
        updatedAtTimestamp = updatedAtTimestamp
    )
}

fun HabitCompletion.toEntity(): HabitCompletionEntity {
    return HabitCompletionEntity(
        id = id,
        habitId = habitId,
        logicalDate = logicalDate,
        status = status.name,
        numericValue = numericValue,
        completedAtTimestamp = completedAtTimestamp,
        note = note,
        updatedAtTimestamp = updatedAtTimestamp
    )
}

fun GoalEntity.toDomain(): Goal {
    return Goal(
        id = id,
        title = title,
        description = description,
        category = category,
        targetDateEpochDay = targetDateEpochDay,
        targetValue = targetValue,
        currentValue = currentValue,
        unit = unit,
        status = GoalStatus.valueOf(status),
        createdAtTimestamp = createdAtTimestamp,
        updatedAtTimestamp = updatedAtTimestamp
    )
}

fun Goal.toEntity(): GoalEntity {
    return GoalEntity(
        id = id,
        title = title,
        description = description,
        category = category,
        targetDateEpochDay = targetDateEpochDay,
        targetValue = targetValue,
        currentValue = currentValue,
        unit = unit,
        status = status.name,
        createdAtTimestamp = createdAtTimestamp,
        updatedAtTimestamp = updatedAtTimestamp
    )
}
