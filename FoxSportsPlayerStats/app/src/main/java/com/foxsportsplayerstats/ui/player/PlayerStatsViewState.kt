package com.foxsportsplayerstats.ui.player

import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.ui.ViewState

data class PlayerStatsViewState(
    val model: PlayerDetailsModel? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
) : ViewState<PlayerDetailsModel>(model, error, isLoading) {

    override fun onLoading(): ViewState<PlayerDetailsModel> =
        copy(isLoading = true, error = null)

    override fun onSuccess(t: PlayerDetailsModel): ViewState<PlayerDetailsModel> =
        copy(model = t, isLoading = false, error = null)

    override fun onError(throwable: Throwable): ViewState<PlayerDetailsModel> =
        copy(error = throwable, isLoading = false)
}

