package com.foxsportsplayerstats.ui.match

import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.domain.model.buildMatchStatModel
import com.foxsportsplayerstats.ui.UiView
import com.nhaarman.mockitokotlin2.any
import org.junit.Test
import org.mockito.Mockito

class MatchStatsViewStateTest {

    @Suppress("UNCHECKED_CAST")
    private val uiView = Mockito.mock(UiView::class.java) as UiView<List<MatchStatModel>>
    private var viewState = MatchStatsViewState()

    @Test
    fun whenLoadingUiViewDisplayLoading() {
        val loadingState = viewState.onLoading()

        loadingState.render(uiView)

        Mockito.verify(uiView).displayProgress()
        Mockito.verify(uiView, Mockito.never()).displayError(any())
        Mockito.verify(uiView, Mockito.never()).displayModel(any())
    }

    @Test
    fun whenErrorUiViewDisplayError() {
        val error = Throwable()
        val errorState = viewState.onError(error)

        errorState.render(uiView)

        Mockito.verify(uiView).displayError(error)
        Mockito.verify(uiView, Mockito.never()).displayProgress()
        Mockito.verify(uiView, Mockito.never()).displayModel(any())
    }

    @Test
    fun whenSuccessUiViewDisplayLoadingHideError() {
        val model = listOf(
            buildMatchStatModel(id = "match_stat1"),
            buildMatchStatModel(id = "match_stat2")
        )
        val errorState = viewState.onSuccess(model)

        errorState.render(uiView)

        Mockito.verify(uiView, Mockito.never()).displayProgress()
        Mockito.verify(uiView, Mockito.never()).displayError(any())
        Mockito.verify(uiView).displayModel(model)
    }
}