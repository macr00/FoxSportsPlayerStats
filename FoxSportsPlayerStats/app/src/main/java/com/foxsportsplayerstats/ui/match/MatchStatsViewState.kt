package com.foxsportsplayerstats.ui.match

import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.ViewState

data class MatchStatsViewState(
    val model: List<MatchStatModel>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
) : ViewState<List<MatchStatModel>>(model, error, isLoading)

