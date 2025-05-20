package com.bimbo.android.presentation.login

import androidx.lifecycle.ViewModel
import com.bimbo.android.di.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> get() = _username

    init {
        _username.value = PreferencesManager.username ?: ""
    }

    fun saveUsername(name: String) {
        PreferencesManager.username = name
    }
}
