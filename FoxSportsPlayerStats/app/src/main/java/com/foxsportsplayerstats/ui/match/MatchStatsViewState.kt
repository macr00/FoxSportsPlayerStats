package com.foxsportsplayerstats.ui.match

import com.foxsportsplayerstats.domain.model.MatchStatsModel
import com.foxsportsplayerstats.ui.UiView

data class MatchStatsViewState(
    val model: MatchStatsModel? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {

    fun render(view: UiView<MatchStatsModel>) {
        view.run {
            displayLoading(isLoading)

            if (model != null)
                displayModel(model)

            if (error != null)
                displayError(error)
        }
    }

}

