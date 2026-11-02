# Rawaq Architecture Guide

## Overview
Rawaq uses a pragmatic single-module architecture (`:app`) following Clean Architecture & Offline-First principles with Room, Hilt, and DataStore Preferences.

## Key Layers & Package Structure
```text
com.rawaq.app/
├── core/
│   ├── theme/          # RawaqTheme, Color tokens, Typography, Shapes
│   └── ui/             # HabitCard, GoalCard, ProgressCard, EmptyStateView
├── data/
│   ├── local/
│   │   ├── dao/        # HabitDao, HabitScheduleRuleDao, HabitCompletionDao, GoalDao
│   │   ├── database/   # RawaqDatabase (Room v1)
│   │   ├── datastore/  # SettingsDataStore (Preferences DataStore)
│   │   ├── entity/     # HabitEntity, HabitScheduleRuleEntity, HabitCompletionEntity, GoalEntity
│   │   └── mapper/     # Entity <-> Domain Mappers
│   └── repository/     # HabitRepositoryImpl, GoalRepositoryImpl, SettingsRepositoryImpl
├── domain/
│   ├── model/          # Pure domain models (Habit, HabitScheduleRule, HabitCompletion, Goal, AppSettings)
│   ├── repository/     # HabitRepository, GoalRepository, SettingsRepository interfaces
│   └── util/           # LogicalDayCalculator, ScheduleEvaluator
├── feature/
│   ├── today/          # TodayScreen & TodayViewModel
│   ├── habits/         # HabitsScreen & HabitsViewModel
│   ├── goals/          # GoalsScreen & GoalsViewModel
│   └── settings/       # SettingsScreen & SettingsViewModel
├── di/                 # DatabaseModule, RepositoryModule, DataStoreModule (Hilt)
└── navigation/         # RawaqNavGraph, RawaqBottomBar, Screen destinations
```

## Data Storage Architecture Rules

1. **Single Source of Truth (SSOT)**:
   - All state is persisted in **Room** (`rawaq.db`) and **Preferences DataStore** (`app_settings`).
   - Streaks, completion rates, and daily progress are computed dynamically from completion event logs.

2. **Logical Day Calculation**:
   - `logicalDate` is stored as an invariant **Epoch Day** (`Int`).
   - Formula: `logicalDate = instant.atZone(zoneId).minusMinutes(offsetMinutes.toLong()).toLocalDate().toEpochDay().toInt()`.
   - Default offset: `180` minutes (03:00 AM).

3. **Schedule Versioning**:
   - Schedule changes append a new `HabitScheduleRuleEntity` with `validFromEpochDay = N` and set `validUntilEpochDay = N - 1` on the prior rule version.
   - Historical completion records and expectations remain intact.

4. **Completion Logs & Unique Constraints**:
   - Unique index `UNIQUE(habit_id, logical_date)` guarantees max 1 status record per habit per logical date.
   - Absence of a completion record for past days is evaluated dynamically as missed without polluting the DB with empty records.

## Dependency Injection
- Powered by **Hilt 2.52**.
- Application class: `@HiltAndroidApp class RawaqApplication`.
- Entry point: `@AndroidEntryPoint class MainActivity`.

## Test Execution Commands
- Run unit tests: `./gradlew app:testDebugUnitTest`
- Assemble debug APK: `./gradlew app:assembleDebug`
