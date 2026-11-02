package com.rawaq.app.di

import com.rawaq.app.data.repository.GoalRepositoryImpl
import com.rawaq.app.data.repository.HabitRepositoryImpl
import com.rawaq.app.data.repository.SettingsRepositoryImpl
import com.rawaq.app.domain.repository.GoalRepository
import com.rawaq.app.domain.repository.HabitRepository
import com.rawaq.app.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHabitRepository(impl: HabitRepositoryImpl): HabitRepository

    @Binds
    @Singleton
    abstract fun bindGoalRepository(impl: GoalRepositoryImpl): GoalRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
