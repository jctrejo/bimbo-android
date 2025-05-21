package com.bimbo.android.common.utils.loader

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.bimbo.android.R
import com.bimbo.android.databinding.LoadDialogBinding

/**
 * Diálogo personalizado que muestra una pantalla de carga.
 *
 * Este diálogo ocupa toda la pantalla con fondo transparente y no es cancelable por el usuario.
 *
 * @param context Contexto en el que se crea el diálogo.
 */
class LoadingDialog(context: Context) : Dialog(context) {

    /**
     * Binding para acceder a las vistas del layout del diálogo de carga.
     */
    private lateinit var binding: LoadDialogBinding

    /**
     * Configura la apariencia y comportamiento del diálogo al crearse.
     * Aplica un tema personalizado, ajusta el tamaño y fondo de la ventana,
     * infla el layout y establece que no se pueda cancelar.
     *
     * @param savedInstanceState Estado previo guardado del diálogo.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context.setTheme(R.style.loaderDialogTheme)
        window?.let {
            it.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
        binding = LoadDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
    }

    /**
     * Muestra el diálogo si no está ya visible.
     */
    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    /**
     * Oculta el diálogo si está visible.
     */
    override fun dismiss() {
        if (isShowing) {
            super.dismiss()
        }
    }
}
