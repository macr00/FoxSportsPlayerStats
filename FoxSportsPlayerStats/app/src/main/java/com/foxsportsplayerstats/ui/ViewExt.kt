package com.foxsportsplayerstats.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.domain.model.TopPlayerStatModel
import com.foxsportsplayerstats.data.network.FoxSportsApi
import com.google.android.material.snackbar.Snackbar

fun ViewGroup.inflate(resId: Int, attach: Boolean): View {
    return LayoutInflater.from(context).inflate(resId, this, attach)
}

fun View.visibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.showErrorSnackbar(t: Throwable) {
    Snackbar.make(this, t.message ?: "ERROR", Snackbar.LENGTH_LONG).show()
}

fun View.loadPlayerHeadShot(imageView: ImageView, url: String) {
    Glide.with(this.context)
        .load(url)
        .centerCrop()
        .listener(object : RequestListener<Drawable> {

            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadEmptyHeadShot(imageView)
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

fun View.loadEmptyHeadShot(imageView: ImageView) {
    Glide.with(this.context)
        .load(FoxSportsApi.getEmptyImgUrl())
        .centerCrop()
        .into(imageView)
}

fun TextView.displayNameAndJumperNo(topPlayer: TopPlayerStatModel) {
    with(topPlayer) {
        text = context.resources.getString(R.string.player_name_jumper, jumperNumber, name)
    }
}

