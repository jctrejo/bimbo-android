package com.bimbo.android.di

import android.content.Context
import android.content.SharedPreferences
import com.bimbo.android.common.Constants.KEY_IS_LOGIN
import com.bimbo.android.common.Constants.KEY_USERNAME
import com.bimbo.android.common.Constants.PREFS_NAME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Objeto singleton que gestiona las preferencias compartidas de la aplicación.
 *
 * Proporciona acceso y actualización reactiva a las preferencias de usuario,
 * como el nombre de usuario y el estado de login, usando [SharedPreferences]
 * y exponiendo [StateFlow] para observar cambios.
 */
object PreferencesManager {

    private lateinit var preferences: SharedPreferences

    /**
     * Flujo que emite el nombre de usuario almacenado en las preferencias.
     */
    private val _usernameFlow = MutableStateFlow<String?>(null)
    val usernameFlow: StateFlow<String?> get() = _usernameFlow

    /**
     * Flujo que emite el estado de login almacenado en las preferencias.
     */
    private val _isLoginFlow = MutableStateFlow(false)
    val isLoginFlow: StateFlow<Boolean> get() = _isLoginFlow

    /**
     * Inicializa el [SharedPreferences] y sincroniza los flujos con los valores almacenados.
     *
     * @param context Contexto de la aplicación para acceder a las preferencias.
     */
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _usernameFlow.value = preferences.getString(KEY_USERNAME, null)
        _isLoginFlow.value = preferences.getBoolean(KEY_IS_LOGIN, false)

        preferences.registerOnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                KEY_USERNAME -> _usernameFlow.value = prefs.getString(KEY_USERNAME, null)
                KEY_IS_LOGIN -> _isLoginFlow.value = prefs.getBoolean(KEY_IS_LOGIN, false)
            }
        }
    }

    /**
     * Propiedad para obtener o establecer el nombre de usuario en las preferencias.
     * Actualiza el flujo [_usernameFlow] al cambiar.
     */
    var username: String?
        get() = preferences.getString(KEY_USERNAME, null)
        set(value) {
            preferences.edit().putString(KEY_USERNAME, value).apply()
            _usernameFlow.value = value
        }

    /**
     * Propiedad para obtener o establecer el estado de login en las preferencias.
     * Actualiza el flujo [_isLoginFlow] al cambiar.
     */
    var isLogin: Boolean
        get() = preferences.getBoolean(KEY_IS_LOGIN, false)
        set(value) {
            preferences.edit().putBoolean(KEY_IS_LOGIN, value).apply()
            _isLoginFlow.value = value
        }
}
