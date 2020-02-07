package com.foxsportsplayerstats.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.showErrorSnackbar(t: Throwable, action: () -> Unit) {
    Snackbar.make(this, t.message ?: "ERROR", Snackbar.LENGTH_INDEFINITE)
        .apply {
            setAction("RETRY") {
                action.invoke()
                dismiss()
            }
            show()
        }
}