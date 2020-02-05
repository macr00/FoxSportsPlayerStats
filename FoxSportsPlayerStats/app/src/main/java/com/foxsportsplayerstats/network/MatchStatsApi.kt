package com.foxsportsplayerstats.network

import io.reactivex.Observable
import retrofit2.http.GET

val MATCH_STATS_BASE_URL = "https://statsapi.foxsports.com.au/3.0/api/sports/league/matches/"

interface MatchStatsApi {

    @GET("NRL20190101/topplayerstats.json;type=fantasy_points;type=tackles;type=runs;type=run_metres?limit=5&userkey=A00239D3-45F6-4A0A-810C-54A347F144C2")
    fun getMatchStats(): Observable<List<MatchStat>>
}