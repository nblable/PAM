package com.example.myfirstkmpapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("is_dark_mode")
    private val SORT_KEY = booleanPreferencesKey("is_sort_by_newest")

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }

    val isSortByNewest: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[SORT_KEY] ?: true
    }

    suspend fun toggleTheme() {
        dataStore.edit { preferences ->
            val current = preferences[THEME_KEY] ?: false
            preferences[THEME_KEY] = !current
        }
    }

    suspend fun setSortOrder(isNewest: Boolean) {
        dataStore.edit { preferences ->
            preferences[SORT_KEY] = isNewest
        }
    }
}