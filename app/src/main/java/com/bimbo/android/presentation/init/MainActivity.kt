package com.bimbo.android.presentation.init

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bimbo.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Actividad principal de la aplicación que sirve como contenedor para la navegación.
 *
 * Utiliza Hilt para la inyección de dependencias mediante la anotación [AndroidEntryPoint].
 * Configura el [NavController] para gestionar la navegación entre fragmentos.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Binding para acceder a las vistas del layout activity_main.xml.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Controlador de navegación que gestiona la navegación entre fragmentos.
     */
    private lateinit var navController: NavController

    /**
     * Método llamado al crear la actividad.
     * Infla el layout usando ViewBinding y configura la vista inicial.
     *
     * @param savedInstanceState Estado previo guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    /**
     * Configura el [NavController] obteniendo el [NavHostFragment] definido en el layout.
     */
    private fun setupView() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
    }
}
