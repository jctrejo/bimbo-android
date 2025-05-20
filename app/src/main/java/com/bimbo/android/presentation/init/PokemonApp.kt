package com.bimbo.android.presentation.init

import android.app.Application
import com.bimbo.android.di.PreferencesManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferencesManager.init(this)
    }
}
