package com.foxsportsplayerstats.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface FoxSportsApi {

    companion object {
        const val BASE_URL = "https://statsapi.foxsports.com.au/3.0/api/sports/league/"
        private const val IMG_URL = "https://media.foxsports.com.au/match-centre/includes/images/headshots/landscape/nrl/"
        private const val IMG_NOT_FOUND_URL = "https://media.foxsports.com.au/match-centre/includes/images/headshots/headshot-blank-large.jpg"

        fun getImgUrl(id: Int): String = "$IMG_URL$id.jpg"

        fun getEmptyImgUrl(): String = IMG_NOT_FOUND_URL

    }

    @GET("matches/NRL20190101/topplayerstats.json;type=fantasy_points;type=tackles;type=runs;type=run_metres?limit=5&userkey=A00239D3-45F6-4A0A-810C-54A347F144C2")
    fun getMatchStats(): Observable<List<MatchStat>>

    @GET("series/1/seasons/117/teams/{team_id}/players/{player_id}/detailedstats.json?&userkey=9024ec15-d791-4bfd-aa3b-5bcf5d36da4f")
    fun getPlayerDetailedStats(@Path("team_id") teamId: String, @Path("player_id") playerId: String): Observable<PlayerDetailedStats>
}