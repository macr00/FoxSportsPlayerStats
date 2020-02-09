package com.foxsportsplayerstats.data

import com.foxsportsplayerstats.data.network.*
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsRequest
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.*

class FoxSportsRepositoryTest {

    private val api = mock(FoxSportsApi::class.java)
    private val repository = FoxSportsRepository(api)

    @Test
    fun getMatchStatsMappedToModel() {
        val teamA = buildTeamData(id = 1)
        val teamB = buildTeamData(id = 2)
        val statData0 = MatchStatData(matchId = "match_id", statType = "run_metres", teamA = teamA, teamB = teamB)
        val statData1 = MatchStatData(matchId = "match_id", statType = "tackles", teamA = teamA, teamB = teamB)

        val actual = listOf(statData0, statData1)

        `when`(api.getMatchStats()).thenReturn(Observable.just(actual))

        val observer = repository.getMatchStats().test()

        verify(api).getMatchStats()

        val expected = observer.values()[0]

        Assert.assertTrue(expected is List<MatchStatModel>)

        Assert.assertEquals(expected[0].id, "match_id")
        Assert.assertEquals(expected[0].statType, "run_metres")
        Assert.assertEquals(expected[0].teamA.id, teamA.id)
        Assert.assertEquals(expected[0].teamB.id, teamB.id)

        Assert.assertEquals(expected[1].id, "match_id")
        Assert.assertEquals(expected[1].statType, "tackles")
        Assert.assertEquals(expected[1].teamA.id, teamA.id)
        Assert.assertEquals(expected[1].teamB.id, teamB.id)
    }

    @Test
    fun getPlayerStatsMappedToModel() {
        val teamId = 1123
        val playerId = 5813
        val request = GetPlayerStatsRequest(teamId, playerId)
        val lastMatchStats = mapOf("goals" to 1, "runs" to 5, "kicks" to 2)
        val actual = buildPlayerData(id = playerId, lastMatchStats = lastMatchStats)

        `when`(api.getPlayerDetailedStats(teamId.toString(), playerId.toString())).thenReturn(Observable.just(actual))

        val observer = repository.getPlayerStats(request).test()

        verify(api).getPlayerDetailedStats(teamId.toString(), playerId.toString())

        val expected = observer.values()[0]

        Assert.assertTrue(expected is PlayerDetailsModel)
        Assert.assertEquals(expected.id, actual.id)
        Assert.assertEquals(expected.surname, actual.surname)
        Assert.assertEquals(expected.fullName, actual.fullName)
        Assert.assertEquals(expected.position, actual.position)
        Assert.assertEquals(expected.lastMatchStats[0].second, actual.lastMatchStats["goals"])
        Assert.assertEquals(expected.lastMatchStats[1].second, actual.lastMatchStats["runs"])
        Assert.assertEquals(expected.lastMatchStats[2].second, actual.lastMatchStats["kicks"])
    }
}