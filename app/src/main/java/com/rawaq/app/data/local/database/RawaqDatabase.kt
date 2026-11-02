package com.rawaq.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rawaq.app.data.local.dao.GoalDao
import com.rawaq.app.data.local.dao.HabitCompletionDao
import com.rawaq.app.data.local.dao.HabitDao
import com.rawaq.app.data.local.dao.HabitScheduleRuleDao
import com.rawaq.app.data.local.entity.GoalEntity
import com.rawaq.app.data.local.entity.HabitCompletionEntity
import com.rawaq.app.data.local.entity.HabitEntity
import com.rawaq.app.data.local.entity.HabitScheduleRuleEntity

@Database(
    entities = [
        HabitEntity::class,
        HabitScheduleRuleEntity::class,
        HabitCompletionEntity::class,
        GoalEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RawaqDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitScheduleRuleDao(): HabitScheduleRuleDao
    abstract fun habitCompletionDao(): HabitCompletionDao
    abstract fun goalDao(): GoalDao
}
