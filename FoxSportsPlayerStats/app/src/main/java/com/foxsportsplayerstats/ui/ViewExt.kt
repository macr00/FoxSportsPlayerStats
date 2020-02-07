package com.foxsportsplayerstats.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.showErrorSnackbar(t: Throwable) {
    Snackbar.make(this, t.message ?: "ERROR", Snackbar.LENGTH_LONG).show()
}