package com.bimbo.android.utils.extensions

import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * Extensión para [Fragment] que navega a la actividad principal especificada,
 * limpiando el stack de actividades para evitar que el usuario pueda volver atrás.
 *
 * Esta función inicia la actividad indicada con las banderas necesarias para
 * crear una nueva tarea y limpiar las actividades existentes,
 * y finaliza la actividad actual.
 *
 * @param mainActivityClass Clase de la actividad principal a la que se desea navegar.
 */
fun Fragment.navigateToMainAndClearStack(mainActivityClass: Class<*>) {
    val intent = Intent(requireContext(), mainActivityClass).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
    requireActivity().finish()
}