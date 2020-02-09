package com.foxsportsplayerstats.domain

import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.usecase.GetPlayerStatsRequest
import io.reactivex.Observable

interface Repository {

    fun getMatchStats(): Observable<List<MatchStatModel>>

    fun getPlayerStats(request: GetPlayerStatsRequest): Observable<PlayerDetailsModel>
}