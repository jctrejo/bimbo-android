package com.bimbo.android.utils.extensions

import android.util.Patterns

/**
 * Extensión para la clase [String] que verifica si la cadena es un email válido.
 *
 * Utiliza el patrón estándar de Android para validar direcciones de correo electrónico.
 *
 * @return `true` si la cadena no está vacía y coincide con el patrón de email válido, `false` en caso contrario.
 */
fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
