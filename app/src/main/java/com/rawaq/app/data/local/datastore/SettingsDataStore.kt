package com.rawaq.app.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rawaq.app.domain.model.AppSettings
import com.rawaq.app.domain.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val LOGICAL_DAY_START_OFFSET_MINUTES = intPreferencesKey("logical_day_start_offset_minutes")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val FIRST_DAY_OF_WEEK = intPreferencesKey("first_day_of_week")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }

    val settingsFlow: Flow<AppSettings> = context.dataStore.data.map { preferences ->
        AppSettings(
            logicalDayStartOffsetMinutes = preferences[Keys.LOGICAL_DAY_START_OFFSET_MINUTES] ?: 180,
            themeMode = try {
                ThemeMode.valueOf(preferences[Keys.THEME_MODE] ?: ThemeMode.SYSTEM.name)
            } catch (e: IllegalArgumentException) {
                ThemeMode.SYSTEM
            },
            firstDayOfWeek = preferences[Keys.FIRST_DAY_OF_WEEK] ?: 1,
            notificationsEnabled = preferences[Keys.NOTIFICATIONS_ENABLED] ?: true
        )
    }

    suspend fun setDayStartOffsetMinutes(offsetMinutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[Keys.LOGICAL_DAY_START_OFFSET_MINUTES] = offsetMinutes
        }
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[Keys.THEME_MODE] = themeMode.name
        }
    }

    suspend fun setFirstDayOfWeek(firstDayOfWeek: Int) {
        context.dataStore.edit { preferences ->
            preferences[Keys.FIRST_DAY_OF_WEEK] = firstDayOfWeek
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[Keys.NOTIFICATIONS_ENABLED] = enabled
        }
    }
}
