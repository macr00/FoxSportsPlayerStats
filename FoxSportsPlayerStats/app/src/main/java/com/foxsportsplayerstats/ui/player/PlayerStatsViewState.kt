package com.foxsportsplayerstats.ui.player

import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.ui.ViewState

data class PlayerStatsViewState(
    val model: PlayerDetailsModel? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
) : ViewState<PlayerDetailsModel>(model, error, isLoading)

