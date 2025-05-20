package com.bimbo.android.di

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PreferencesManager {

    private const val PREFS_NAME = "app_prefs"
    private const val KEY_USERNAME = "usuario"

    private lateinit var preferences: SharedPreferences

    private val _usernameFlow = MutableStateFlow<String?>(null)
    val usernameFlow: StateFlow<String?> get() = _usernameFlow

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _usernameFlow.value = preferences.getString(KEY_USERNAME, null)

        preferences.registerOnSharedPreferenceChangeListener { prefs, key ->
            if (key == KEY_USERNAME) {
                _usernameFlow.value = prefs.getString(KEY_USERNAME, null)
            }
        }
    }

    var username: String?
        get() = preferences.getString(KEY_USERNAME, null)
        set(value) {
            preferences.edit().putString(KEY_USERNAME, value).apply()
            _usernameFlow.value = value
        }
}