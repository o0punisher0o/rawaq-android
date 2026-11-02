package com.rawaq.app.domain.repository

import com.rawaq.app.domain.model.AppSettings
import com.rawaq.app.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeSettings(): Flow<AppSettings>
    suspend fun updateDayStartOffsetMinutes(offsetMinutes: Int)
    suspend fun updateThemeMode(themeMode: ThemeMode)
    suspend fun updateFirstDayOfWeek(firstDayOfWeek: Int)
    suspend fun updateNotificationsEnabled(enabled: Boolean)
}
