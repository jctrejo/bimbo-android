package com.bimbo.android.utils.extensions

import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.navigateToMainAndClearStack(mainActivityClass: Class<*>) {
    val intent = Intent(requireContext(), mainActivityClass).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
    requireActivity().finish()
}
