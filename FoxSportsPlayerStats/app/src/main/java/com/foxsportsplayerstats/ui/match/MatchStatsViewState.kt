package com.foxsportsplayerstats.ui.match

import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.ViewState

data class MatchStatsViewState(
    val model: List<MatchStatModel>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false
) : ViewState<List<MatchStatModel>>(model, error, isLoading) {

    override fun onLoading(): ViewState<List<MatchStatModel>> =
        copy(isLoading = true, error = null)

    override fun onSuccess(t: List<MatchStatModel>): ViewState<List<MatchStatModel>> =
        copy(isLoading = false, error = null, model = t)

    override fun onError(throwable: Throwable): ViewState<List<MatchStatModel>> =
        copy(isLoading = false, error = throwable)
}

