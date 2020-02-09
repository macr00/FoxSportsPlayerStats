package com.foxsportsplayerstats.ui.match

import com.foxsportsplayerstats.domain.usecase.GetMatchStatsRequest
import com.foxsportsplayerstats.domain.usecase.GetMatchStatsUseCase
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.BaseViewModel
import javax.inject.Inject

class MatchStatsViewModel
@Inject
constructor(
    private val getMatchStatsUseCase: GetMatchStatsUseCase
) : BaseViewModel<List<MatchStatModel>>() {

    companion object {
        const val TAG = "MatchStatsViewModel"
    }

    init {
        viewState = MatchStatsViewState()
    }

    fun loadMatchStats() {
        getMatchStatsUseCase.apply(GetMatchStatsRequest)
            .subscribe( { response -> accept(response) }, { t -> onError(t, TAG) })
            .disposeOnCleared()
    }
}