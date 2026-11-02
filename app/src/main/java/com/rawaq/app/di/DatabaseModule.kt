package com.rawaq.app.di

import android.content.Context
import androidx.room.Room
import com.rawaq.app.data.local.dao.GoalDao
import com.rawaq.app.data.local.dao.HabitCompletionDao
import com.rawaq.app.data.local.dao.HabitDao
import com.rawaq.app.data.local.dao.HabitScheduleRuleDao
import com.rawaq.app.data.local.database.RawaqDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRawaqDatabase(@ApplicationContext context: Context): RawaqDatabase {
        return Room.databaseBuilder(
            context,
            RawaqDatabase::class.java,
            "rawaq.db"
        ).build()
    }

    @Provides
    fun provideHabitDao(database: RawaqDatabase): HabitDao = database.habitDao()

    @Provides
    fun provideHabitScheduleRuleDao(database: RawaqDatabase): HabitScheduleRuleDao = database.habitScheduleRuleDao()

    @Provides
    fun provideHabitCompletionDao(database: RawaqDatabase): HabitCompletionDao = database.habitCompletionDao()

    @Provides
    fun provideGoalDao(database: RawaqDatabase): GoalDao = database.goalDao()
}
