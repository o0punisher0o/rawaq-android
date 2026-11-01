# Rawaq (رواق) — Intentional Growth & Habit Scaffold

**Rawaq** is a clean, tranquil Android starter application scaffold designed for daily habits, goals, and mindful personal growth.

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose + Material 3
- **Navigation**: Navigation Compose (`RawaqNavGraph`, `RawaqBottomBar`)
- **Architecture**: Single-module (`:app`), ViewModel + StateFlow pattern
- **Local Storage Preparedness**: Room Database & Preferences DataStore dependencies included
- **Design Tokens**: Custom M3 theme with light cream (`#FBF9F5`) and dark sage (`#121413`) palettes
- **Build System**: Gradle Kotlin DSL (`.gradle.kts`) + Version Catalog (`gradle/libs.versions.toml`)
- **Testing**: JUnit 4, Kotlinx Coroutines Test, Compose UI Testing

## Architecture & Structure
```text
com.rawaq.app/
├── core/
│   ├── theme/          # RawaqTheme, Color tokens, Typography, Shapes
│   └── ui/             # HabitCard, GoalCard, ProgressCard, EmptyStateView
├── data/
│   └── model/          # Habit, Goal, DailyProgress domain models
├── feature/
│   ├── today/          # TodayScreen & TodayViewModel
│   ├── habits/         # HabitsScreen & HabitsViewModel
│   ├── goals/          # GoalsScreen & GoalsViewModel
│   └── settings/       # SettingsScreen & SettingsViewModel
└── navigation/         # RawaqNavGraph, RawaqBottomBar, Screen destinations
```

## Local Build & Test Instructions
To compile and assemble the debug APK:
```bash
./gradlew assembleDebug
```

To run unit tests:
```bash
./gradlew testDebugUnitTest
```

## Project Status
This repository contains the initial starter scaffold for Rawaq. Habit creation workflows, database migrations, notifications, and analytics will be implemented in subsequent development phases.
