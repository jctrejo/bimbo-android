package com.bimbo.android.presentation.init

import android.app.Application
import com.bimbo.android.di.PreferencesManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Clase principal de la aplicación que extiende [Application].
 *
 * Esta clase está anotada con [HiltAndroidApp] para habilitar la integración
 * de Hilt y realizar la inyección de dependencias a nivel de aplicación.
 *
 * Se encarga de inicializar componentes globales, como el [PreferencesManager].
 */
@HiltAndroidApp
class PokemonApp : Application() {

    /**
     * Método llamado cuando se crea la aplicación.
     * Inicializa el [PreferencesManager] con el contexto de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        PreferencesManager.init(this)
    }
}
