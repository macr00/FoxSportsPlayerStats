package com.foxsportsplayerstats.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchStat(
    @Json(name = "match_id") val matchId: String,
    @Json(name = "team_A") val teamA: Team,
    @Json(name = "team_B") val teamB: Team,
    @Json(name = "stat_type") val statType: String
)