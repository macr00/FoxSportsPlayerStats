package com.foxsportsplayerstats.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.ui.inflate
import com.foxsportsplayerstats.ui.visibleOrGone
import kotlinx.android.synthetic.main.progress_retry_layout.view.*

class ProgressRetryLayout
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(R.layout.progress_retry_layout, true)
    }

    fun showProgress(show: Boolean) {
        visibility = View.VISIBLE
        progress.visibleOrGone(show)
        retry_btn.visibleOrGone(!show)
    }

    fun showRetry(show: Boolean) {
        visibility = View.VISIBLE
        retry_btn.visibleOrGone(show)
        progress.visibleOrGone(!show)
    }

    fun setRetryClickListener(action: () -> Unit) {
        retry_btn.setOnClickListener {
            action.invoke()
            retry_btn.visibleOrGone(false)
        }
    }
}