package com.rawaq.app.domain.model

data class Habit(
    val id: String,
    val title: String,
    val description: String? = null,
    val category: String,
    val targetType: TargetType = TargetType.BINARY,
    val targetValue: Double = 1.0,
    val unit: String? = null,
    val colorHex: String? = null,
    val iconName: String? = null,
    val isArchived: Boolean = false,
    val archivedAtEpochDay: Int? = null,
    val excludeFromAnalytics: Boolean = false,
    val sortOrder: Int = 0,
    val createdAtTimestamp: Long = System.currentTimeMillis(),
    val updatedAtTimestamp: Long = System.currentTimeMillis()
)

data class HabitScheduleRule(
    val id: String,
    val habitId: String,
    val scheduleType: ScheduleType,
    val daysOfWeekMask: Int? = null,
    val validFromEpochDay: Int,
    val validUntilEpochDay: Int? = null,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

data class HabitCompletion(
    val id: String,
    val habitId: String,
    val logicalDate: Int,
    val status: CompletionStatus,
    val numericValue: Double? = null,
    val completedAtTimestamp: Long = System.currentTimeMillis(),
    val note: String? = null,
    val updatedAtTimestamp: Long = System.currentTimeMillis()
)

data class DailyHabitStatus(
    val habit: Habit,
    val activeScheduleRule: HabitScheduleRule,
    val isDue: Boolean,
    val completion: HabitCompletion?
)

data class Goal(
    val id: String,
    val title: String,
    val description: String? = null,
    val category: String? = null,
    val targetDateEpochDay: Int? = null,
    val targetValue: Double = 1.0,
    val currentValue: Double = 0.0,
    val unit: String? = null,
    val status: GoalStatus = GoalStatus.IN_PROGRESS,
    val createdAtTimestamp: Long = System.currentTimeMillis(),
    val updatedAtTimestamp: Long = System.currentTimeMillis()
)

data class AppSettings(
    val logicalDayStartOffsetMinutes: Int = 180,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val firstDayOfWeek: Int = 1,
    val notificationsEnabled: Boolean = true
)
