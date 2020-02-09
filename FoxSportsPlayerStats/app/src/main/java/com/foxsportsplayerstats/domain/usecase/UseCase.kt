package com.foxsportsplayerstats.domain.usecase

import com.foxsportsplayerstats.domain.RxSchedulers
import com.foxsportsplayerstats.domain.UseCaseRequest
import com.foxsportsplayerstats.domain.UseCaseResponse
import io.reactivex.Observable

interface UseCase<R, T> where R : UseCaseRequest {

    fun apply(request: R): Observable<UseCaseResponse<T>>
}

abstract class BaseUseCase<R, T>(
    private val schedulers: RxSchedulers
) : UseCase<R, T> where R : UseCaseRequest {

    abstract fun useCaseObservable(request: R): Observable<UseCaseResponse<T>>

    override fun apply(request: R): Observable<UseCaseResponse<T>> {
        return useCaseObservable(request)
            .onErrorReturn { t: Throwable -> UseCaseResponse.Error(t) }
            .startWith(UseCaseResponse.Loading())
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
    }
}