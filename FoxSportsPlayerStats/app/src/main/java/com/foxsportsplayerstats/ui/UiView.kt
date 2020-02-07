package com.foxsportsplayerstats.ui

interface UiView<T> {

    fun displayModel(model: T)

    fun displayProgress(isLoading: Boolean)

    fun displayError(throwable: Throwable)

    fun hideProgressRetry()
}