package com.foxsportsplayerstats.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.ui.gone
import com.foxsportsplayerstats.ui.inflate
import com.foxsportsplayerstats.ui.visible
import kotlinx.android.synthetic.main.progress_retry_layout.view.*

class ProgressRetryLayout
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(R.layout.progress_retry_layout, true)
    }

    fun showProgress() {
        this.visible()
        progress.visible()
        retry_btn.gone()
    }

    fun showRetry() {
        this.visible()
        retry_btn.visible()
        progress.gone()
    }

    fun setRetryClickListener(action: () -> Unit) {
        retry_btn.setOnClickListener {
            action.invoke()
            retry_btn.gone()
        }
    }
}