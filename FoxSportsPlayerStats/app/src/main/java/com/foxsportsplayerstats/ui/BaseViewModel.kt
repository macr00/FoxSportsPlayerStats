package com.foxsportsplayerstats.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.foxsportsplayerstats.domain.UseCaseResponse
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<T> : ViewModel() {

    protected lateinit var viewState: ViewState<T>
    private val viewStatePublisher = BehaviorSubject.create<ViewState<T>>()

    private val disposables = CompositeDisposable()

    fun accept(response: UseCaseResponse<T>?) {
        response?.let {
            viewState = when(it) {
                is UseCaseResponse.Loading -> viewState.onLoading()
                is UseCaseResponse.Success -> viewState.onSuccess(it.model)
                is UseCaseResponse.Error -> viewState.onError(it.throwable)
            }
            viewStatePublisher.onNext(viewState)
        }
    }

    fun viewStateObservable(): Observable<ViewState<T>> = viewStatePublisher
            .onErrorResumeNext(Observable.empty())
            .distinctUntilChanged()
            .hide()

    fun onError(t: Throwable, tag: String) {
        Log.e(tag, t.localizedMessage ?: "Unknown Error!")
        viewState = viewState.onError(t)
        viewStatePublisher.onNext(viewState)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun Disposable.disposeOnCleared() {
        disposables.add(this)
    }
}