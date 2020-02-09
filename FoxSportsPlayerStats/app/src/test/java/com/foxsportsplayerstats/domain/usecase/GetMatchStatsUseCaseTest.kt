package com.foxsportsplayerstats.domain.usecase

import com.foxsportsplayerstats.RxSchedulersTesting
import com.foxsportsplayerstats.domain.Repository
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.domain.model.buildMatchStat
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.*


class GetMatchStatsUseCaseTest {

    private val testScheduler = TestScheduler()
    private val schedulers = RxSchedulersTesting(testScheduler)
    private val repository = mock(Repository::class.java)

    private val getMatchStats = GetMatchStatsUseCase(schedulers, repository)

    @Test
    fun loadingThenSuccessResponseWhenRepositoryReturnsModel() {
        val matchStat0 = buildMatchStat(id = "match_stat_0")
        val matchStat1 = buildMatchStat(id = "match_stat_1")
        val matchStat2 = buildMatchStat(id = "match_stat_2")
        val actual = listOf(matchStat0, matchStat1, matchStat2)

        `when`(repository.getMatchStats()).thenReturn(Observable.just(actual))

        val observer = getMatchStats.apply(GetMatchStatsRequest).test()
        testScheduler.triggerActions()

        verify(repository).getMatchStats()

        val expected = observer.values()[1]
        val model = (expected as UseCaseResponse.Success).model

        Assert.assertTrue(observer.values()[0] is UseCaseResponse.Loading)
        Assert.assertEquals(UseCaseResponse.Success(actual), expected)
        Assert.assertEquals(model[0].id, actual[0].id)
        Assert.assertEquals(model[1].id, actual[1].id)
        Assert.assertEquals(model[2].id, actual[2].id)

        observer.assertComplete()
        observer.assertNoErrors()
    }

    @Test
    fun loadingThenErrorResponseWhenRepositoryReturnsError() {
        val error = Throwable()

        `when`(repository.getMatchStats()).thenReturn(Observable.error(error))

        val observer = getMatchStats.apply(GetMatchStatsRequest).test()
        testScheduler.triggerActions()

        verify(repository).getMatchStats()

        observer.assertComplete()
        observer.assertNoErrors()

        Assert.assertTrue(observer.values()[0] is UseCaseResponse.Loading)
        Assert.assertEquals(UseCaseResponse.Error<MatchStatModel>(error), observer.values()[1])
    }
}