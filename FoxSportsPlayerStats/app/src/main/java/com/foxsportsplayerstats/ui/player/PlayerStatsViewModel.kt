package com.foxsportsplayerstats.ui.player

import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsRequest
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsUseCase
import com.foxsportsplayerstats.ui.BaseViewModel
import javax.inject.Inject

class PlayerStatsViewModel
@Inject
constructor(
    private val getPlayerStatsUseCase: GetPlayerStatsUseCase
) : BaseViewModel<PlayerDetailsModel>() {

    companion object {
        const val TAG = "PlayerStatsViewModel"
    }

    init {
        viewState = PlayerStatsViewState()
    }

    fun loadPlayer(teamId: Int, playerId: Int) {
        getPlayerStatsUseCase.apply(GetPlayerStatsRequest(teamId, playerId))
            .subscribe( { response -> accept(response) }, { t -> onError(t, TAG) })
            .disposeOnCleared()
    }
}