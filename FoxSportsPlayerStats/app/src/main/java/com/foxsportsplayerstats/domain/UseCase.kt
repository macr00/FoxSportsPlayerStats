package com.foxsportsplayerstats.domain

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

interface UseCase<R,T> : ObservableTransformer<R, UseCaseResponse<T>> where R: UseCaseRequest

abstract class BaseUseCase<R,T>(
    private val schedulers: RxSchedulers
) : UseCase<R,T> where R : UseCaseRequest {

    abstract fun useCaseObservable(upstream: Observable<R>): Observable<UseCaseResponse<T>>

    override fun apply(upstream: Observable<R>): Observable<UseCaseResponse<T>> {
        return useCaseObservable(upstream)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
    }
}