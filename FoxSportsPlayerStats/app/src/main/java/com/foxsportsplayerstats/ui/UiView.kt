package com.foxsportsplayerstats.ui

interface UiView<T> {

    fun displayModel(model: T)

    fun displayLoading(isLoading: Boolean)

    fun displayError(throwable: Throwable)
}