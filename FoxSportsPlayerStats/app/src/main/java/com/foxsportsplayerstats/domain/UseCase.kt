package com.foxsportsplayerstats.domain

import io.reactivex.Observable

interface UseCase<R,T> where R: UseCaseRequest {

    fun apply(request: R): Observable<UseCaseResponse<T>>
}

abstract class BaseUseCase<R,T>(
    private val schedulers: RxSchedulers
) : UseCase<R,T> where R : UseCaseRequest {

    abstract fun useCaseObservable(request: R): Observable<UseCaseResponse<T>>

    override fun apply(request: R): Observable<UseCaseResponse<T>> {
        return useCaseObservable(request)
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
    }
}