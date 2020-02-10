package com.foxsportsplayerstats.ui.player

import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.model.buildPlayerDetailsModel
import com.foxsportsplayerstats.ui.UiView
import com.nhaarman.mockitokotlin2.any
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.never



class PlayerStatsViewStateTest {

    @Suppress("UNCHECKED_CAST")
    private val uiView = mock(UiView::class.java) as UiView<PlayerDetailsModel>
    private var viewState = PlayerStatsViewState()

    @Test
    fun whenLoadingUiViewDisplayLoading() {
        val loadingState = viewState.onLoading()

        loadingState.render(uiView)

        verify(uiView).displayProgress()
        verify(uiView, never()).displayError(any())
        verify(uiView, never()).displayModel(any())
    }

    @Test
    fun whenErrorUiViewDisplayError() {
        val error = Throwable()
        val errorState = viewState.onError(error)

        errorState.render(uiView)

        verify(uiView).displayError(error)
        verify(uiView, never()).displayProgress()
        verify(uiView, never()).displayModel(any())
    }

    @Test
    fun whenSuccessUiViewDisplayModelHideProgressRetry() {
        val model = buildPlayerDetailsModel()
        val errorState = viewState.onSuccess(model)

        errorState.render(uiView)

        verify(uiView, never()).displayProgress()
        verify(uiView, never()).displayError(any())
        verify(uiView).displayModel(model)
    }
}