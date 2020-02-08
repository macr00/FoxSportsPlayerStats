package com.foxsportsplayerstats.ui

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.FoxSportsApi
import com.google.android.material.snackbar.Snackbar

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.showErrorSnackbar(t: Throwable) {
    Snackbar.make(this, t.message ?: "ERROR", Snackbar.LENGTH_LONG).show()
}

fun View.loadPlayerHeadShot(imageView: ImageView, url: String) {
    Glide.with(this.context)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .listener(object : RequestListener<Drawable> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadPlayerHeadShot(imageView, FoxSportsApi.getEmptyImgUrl())
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .into(imageView)
}

fun String.capitalizeWords(): String = split("_").map { it.capitalize() }.joinToString(" ")

