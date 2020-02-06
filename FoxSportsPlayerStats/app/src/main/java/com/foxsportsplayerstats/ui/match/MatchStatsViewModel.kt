package com.foxsportsplayerstats.ui.match

import android.util.Log
import androidx.lifecycle.ViewModel
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.match.GetMatchStatsRequest
import com.foxsportsplayerstats.domain.match.GetMatchStatsUseCase
import com.foxsportsplayerstats.domain.model.MatchStatsModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MatchStatsViewModel
@Inject
constructor(
    private val getMatchStatsUseCase: GetMatchStatsUseCase
) : ViewModel() {

    companion object {
        const val TAG = "MatchStatsViewModel"
    }

    private val disposables = CompositeDisposable()
    private var viewState = MatchStatsViewState()

    // BehaviorSubject so view state is emitted when subscribed (after config changes)
    private val viewStatePublisher = BehaviorSubject.create<MatchStatsViewState>()
    // PublishSubject to prevent error emitted on config changes
    private val errorPublisher = PublishSubject.create<Throwable>()

    init {
        loadMatchStats()
    }

    fun loadMatchStats() {
        disposables.add(getMatchStatsUseCase.apply(GetMatchStatsRequest)
            .subscribe({ response ->
                when (response) {
                    is UseCaseResponse.Loading -> onLoading()
                    is UseCaseResponse.Success -> onSuccess(response.data)
                    is UseCaseResponse.Error -> onError(response.throwable)
                }
            }) { throwable ->
                Log.e(TAG, throwable.message ?: "Unknown Error")
                onError(throwable)
            }
        )
    }

    fun matchStatsObservable(): Observable<MatchStatsViewState> =
        viewStatePublisher
            .startWith(viewState)
            .onErrorResumeNext(Observable.empty())
            .hide()

    fun errorObservable(): Observable<Throwable> =
        errorPublisher
            .onErrorResumeNext(Observable.empty())
            .hide()

    private fun onLoading() {
        viewState = viewState.copy(isLoading = true)
        viewStatePublisher.onNext(viewState)
    }

    private fun onSuccess(model: MatchStatsModel) {
        viewState = viewState.copy(isLoading = false, log = model.toString())
        viewStatePublisher.onNext(viewState)
    }

    private fun onError(throwable: Throwable) {
        errorPublisher.onNext(throwable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}