package com.foxsportsplayerstats.ui.player

import com.foxsportsplayerstats.domain.model.PlayerStatsModel
import com.foxsportsplayerstats.ui.UiView

data class PlayerStatsViewState(
    val model: PlayerStatsModel? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
) {

    fun render(view: UiView<PlayerStatsModel>) {
        view.run {
            displayLoading(isLoading)

            if (model != null) {
                displayModel(model)
            }

            if (error != null) {
                displayError(error)
            }
        }
    }

}

