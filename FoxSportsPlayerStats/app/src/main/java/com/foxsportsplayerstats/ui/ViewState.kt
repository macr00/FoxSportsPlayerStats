package com.foxsportsplayerstats.ui

open class ViewState<T>(
    private val model: T? = null,
    private val error: Throwable? = null,
    private val isLoading: Boolean = false
) {

    fun render(view: UiView<T>) {
        view.run {
            if (model != null) {
                hideProgressRetry()
                displayModel(model)
            }

            if (isLoading)
                displayProgress()

            if (error != null)
                displayError(error)
        }
    }
}