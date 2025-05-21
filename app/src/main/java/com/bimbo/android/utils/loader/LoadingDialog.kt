package com.bimbo.android.utils.loader

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.bimbo.android.R
import com.bimbo.android.databinding.LoadDialogBinding

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var binding: LoadDialogBinding

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

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    override fun dismiss() {
        if (isShowing) {
            super.dismiss()
        }
    }
}
