package com.foxsportsplayerstats.ui.match

import android.util.Log
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.usecase.GetMatchStatsRequest
import com.foxsportsplayerstats.domain.usecase.GetMatchStatsUseCase
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class MatchStatsViewModel
@Inject
constructor(
    private val getMatchStatsUseCase: GetMatchStatsUseCase
) : BaseViewModel() {

    companion object {
        const val TAG = "MatchStatsViewModel"
    }

    private var viewState = MatchStatsViewState()
    private val viewStatePublisher = BehaviorSubject.create<MatchStatsViewState>()

    init {
        loadMatchStats()
    }

    fun loadMatchStats() {
        getMatchStatsUseCase.apply(GetMatchStatsRequest)
            .subscribe({ response ->
                when (response) {
                    is UseCaseResponse.Loading -> onLoading()
                    is UseCaseResponse.Success -> onSuccess(response.model)
                    is UseCaseResponse.Error -> onError(response.throwable)
                }
            }, { throwable ->
                onError(throwable)
                Log.e(TAG, throwable.message ?: "Unknown Error")
            })
            .disposeOnCleared()
    }

    fun matchStatsObservable(): Observable<MatchStatsViewState> =
        viewStatePublisher
            .startWith(viewState)
            .onErrorResumeNext(Observable.empty())
            .hide()

    private fun onLoading() {
        viewState = viewState.copy(isLoading = true, error = null)
        viewStatePublisher.onNext(viewState)
    }

    private fun onSuccess(model: List<MatchStatModel>) {
        viewState = viewState.copy(isLoading = false, error = null, model = model)
        viewStatePublisher.onNext(viewState)
    }

    private fun onError(throwable: Throwable) {
        viewState = viewState.copy(isLoading = false, error = throwable)
        viewStatePublisher.onNext(viewState)
    }
}