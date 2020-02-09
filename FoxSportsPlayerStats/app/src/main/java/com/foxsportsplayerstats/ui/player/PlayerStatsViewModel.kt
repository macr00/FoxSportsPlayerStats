package com.foxsportsplayerstats.ui.player

import android.util.Log
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsRequest
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsUseCase
import com.foxsportsplayerstats.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class PlayerStatsViewModel
@Inject
constructor(
    private val getPlayerStatsUseCase: GetPlayerStatsUseCase
) : BaseViewModel() {

    companion object {
        const val TAG = "PlayerDetailsViewModel"
    }

    private var viewState = PlayerStatsViewState()
    private val viewStatePublisher = BehaviorSubject.create<PlayerStatsViewState>()

    fun loadPlayer(teamId: Int, playerId: Int) {
        getPlayerStatsUseCase.apply(
            GetPlayerStatsRequest(
                teamId = teamId,
                playerId = playerId
            )
        )
            .subscribe({ response ->
                when(response) {
                    is UseCaseResponse.Loading -> onLoading()
                    is UseCaseResponse.Success -> onSuccess(response.model)
                    is UseCaseResponse.Error -> onError(response.throwable)
                }
            }, { throwable ->
                onError(throwable)
                Log.e(TAG, throwable.localizedMessage ?: "Unknown Error")
            })
            .disposeOnCleared()
    }

    fun playerStatsObservable(): Observable<PlayerStatsViewState> =
        viewStatePublisher
            .startWith(viewState)
            .onErrorResumeNext(Observable.empty())
            .hide()

    private fun onLoading() {
        viewState = viewState.copy(isLoading = true, error = null)
        viewStatePublisher.onNext(viewState)
    }

    private fun onSuccess(model: PlayerDetailsModel) {
        viewState = viewState.copy(isLoading = false, error = null, model = model)
        viewStatePublisher.onNext(viewState)
    }

    private fun onError(throwable: Throwable) {
        viewState = viewState.copy(isLoading = false, error = throwable)
        viewStatePublisher.onNext(viewState)
    }
}