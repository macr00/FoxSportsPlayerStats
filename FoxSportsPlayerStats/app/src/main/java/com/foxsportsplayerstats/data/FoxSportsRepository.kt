package com.foxsportsplayerstats.data

import com.foxsportsplayerstats.data.network.FoxSportsApi
import com.foxsportsplayerstats.data.network.toModel
import com.foxsportsplayerstats.domain.Repository
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsRequest
import io.reactivex.Observable
import javax.inject.Inject

class FoxSportsRepository
@Inject
constructor(private val api: FoxSportsApi): Repository {

    override fun getMatchStats(): Observable<List<MatchStatModel>> {
        return api.getMatchStats().map { data -> data.map { it.toModel() } }
    }

    override fun getPlayerStats(request: GetPlayerStatsRequest): Observable<PlayerDetailsModel> {
        return api.getPlayerDetailedStats(
            teamId = request.teamId.toString(),
            playerId = request.playerId.toString()
        ).map { data -> data.toModel() }
    }
}