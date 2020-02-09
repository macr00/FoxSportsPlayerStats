package com.foxsportsplayerstats.ui.match

import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.UiView

data class MatchStatsViewState(
    val model: List<MatchStatModel>? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {

    fun render(view: UiView<List<MatchStatModel>>) {
        view.run {
            displayProgress(isLoading)

            if (error != null)
                displayError(error)

            if (model != null) {
                hideProgressRetry()
                displayModel(model)
            }
        }
    }
}

