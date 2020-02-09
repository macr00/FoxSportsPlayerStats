package com.foxsportsplayerstats.domain.usecase

import com.foxsportsplayerstats.RxSchedulersTesting
import com.foxsportsplayerstats.domain.Repository
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.domain.model.buildPlayerDetailsModel
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.*

class GetPlayerStatsRequestTest {

    private val testScheduler = TestScheduler()
    private val scheduler = RxSchedulersTesting(testScheduler)
    private val repository = mock(Repository::class.java)
    private val request = GetPlayerStatsRequest(teamId = 123, playerId = 456)

    private val getPlayerStats = GetPlayerStatsUseCase(scheduler, repository)

    @Test
    fun loadingThenSuccessResponseWhenRepositoryReturnsModel() {
        val actual = buildPlayerDetailsModel(id = 123)

        `when`(repository.getPlayerStats(request)).thenReturn(Observable.just(actual))

        val observer = getPlayerStats.apply(request).test()
        testScheduler.triggerActions()

        verify(repository).getPlayerStats(request)

        val expected = observer.values()[1]
        val model = (expected as UseCaseResponse.Success).model

        Assert.assertTrue(observer.values()[0] is UseCaseResponse.Loading)
        Assert.assertEquals(UseCaseResponse.Success(actual), expected)
        Assert.assertEquals(model.id, actual.id)

        observer.assertComplete()
        observer.assertNoErrors()
    }

    @Test
    fun loadingThenErrorResponseWhenRepositoryReturnsError() {
        val error = Throwable()

        `when`(repository.getPlayerStats(request)).thenReturn(Observable.error(error))

        val observer = getPlayerStats.apply(request).test()
        testScheduler.triggerActions()

        verify(repository).getPlayerStats(request)

        observer.assertComplete()
        observer.assertNoErrors()

        Assert.assertTrue(observer.values()[0] is UseCaseResponse.Loading)
        Assert.assertEquals(UseCaseResponse.Error<MatchStatModel>(error), observer.values()[1])
    }
}