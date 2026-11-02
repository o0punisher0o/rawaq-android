package com.rawaq.app.domain.model

enum class TargetType {
    BINARY,
    NUMERIC
}

enum class ScheduleType {
    DAILY,
    WEEKLY_DAYS,
    PAUSED
}

enum class CompletionStatus {
    COMPLETED,
    PARTIAL,
    SKIPPED,
    CANCELLED
}

enum class GoalStatus {
    IN_PROGRESS,
    COMPLETED,
    ARCHIVED
}

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}
