package com.bimbo.android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimbo.android.common.Constants.EMPTY
import com.bimbo.android.di.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para manejar la lógica de login de la aplicación.
 *
 * Utiliza Hilt para inyección de dependencias mediante la anotación [HiltViewModel].
 * Expone el nombre de usuario como [StateFlow] y [LiveData] para facilitar la observación desde la UI.
 *
 * @constructor Inyecta las dependencias necesarias.
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    /**
     * Estado interno que contiene el nombre de usuario, puede ser nulo inicialmente.
     */
    private val _username = MutableStateFlow<String?>(null)

    /**
     * Estado observable que expone el nombre de usuario.
     */
    val username: StateFlow<String?> get() = _username

    /**
     * LiveData que expone el nombre de usuario para facilitar la observación en componentes Java.
     */
    private val _usernameLiveData = MutableLiveData<String>()
    val usernameLiveData: LiveData<String> get() = _usernameLiveData

    init {
        // Recoge los valores del Flow username y los publica en el LiveData para compatibilidad.
        viewModelScope.launch {
            username.collect { name ->
                _usernameLiveData.postValue(name ?: EMPTY)
            }
        }
    }

    /**
     * Guarda el nombre de usuario en las preferencias compartidas.
     *
     * @param name Nombre de usuario a guardar.
     */
    fun saveUsername(name: String) {
        PreferencesManager.username = name
    }

    /**
     * Guarda el estado de login en las preferencias compartidas.
     *
     * @param isLogin Estado de login a guardar.
     */
    fun saveLogin(isLogin: Boolean) {
        PreferencesManager.isLogin = isLogin
    }

    /**
     * Obtiene el estado actual de login desde las preferencias compartidas.
     *
     * @return `true` si el usuario está logueado, `false` en caso contrario.
     */
    fun getIsLogin(): Boolean = PreferencesManager.isLoginFlow.value
}
