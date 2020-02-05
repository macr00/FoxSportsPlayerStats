package com.foxsportsplayerstats.domain

sealed class UseCaseResponse<T> {

    class Loading<T>: UseCaseResponse<T>()

    data class Error<T>(val throwable: Throwable): UseCaseResponse<T>()

    data class Success<T>(val data: T): UseCaseResponse<T>()
}