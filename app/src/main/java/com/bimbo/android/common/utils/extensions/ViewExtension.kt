package com.bimbo.android.common.utils.extensions

import android.view.View

/**
 * Extensión para la clase [View] que simplifica la asignación de un listener de clic.
 *
 * Permite pasar una lambda sin parámetros que se ejecutará cuando se haga clic en la vista.
 *
 * @param onClickListener Lambda que se ejecuta al hacer clic en la vista.
 */
fun View.onClick(onClickListener: () -> Unit) {
    this.setOnClickListener {
        onClickListener()
    }
}