package com.rawaq.app.data.repository

import com.rawaq.app.data.local.datastore.SettingsDataStore
import com.rawaq.app.domain.model.AppSettings
import com.rawaq.app.domain.model.ThemeMode
import com.rawaq.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {

    override fun observeSettings(): Flow<AppSettings> {
        return settingsDataStore.settingsFlow
    }

    override suspend fun updateDayStartOffsetMinutes(offsetMinutes: Int) {
        settingsDataStore.setDayStartOffsetMinutes(offsetMinutes)
    }

    override suspend fun updateThemeMode(themeMode: ThemeMode) {
        settingsDataStore.setThemeMode(themeMode)
    }

    override suspend fun updateFirstDayOfWeek(firstDayOfWeek: Int) {
        settingsDataStore.setFirstDayOfWeek(firstDayOfWeek)
    }

    override suspend fun updateNotificationsEnabled(enabled: Boolean) {
        settingsDataStore.setNotificationsEnabled(enabled)
    }
}
