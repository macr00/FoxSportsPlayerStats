package com.foxsportsplayerstats.network

import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchStatData(
    @Json(name = "match_id") val matchId: String,
    @Json(name = "team_A") val teamA: TeamData,
    @Json(name = "team_B") val teamB: TeamData,
    @Json(name = "stat_type") val statType: String
)

fun MatchStatData.toModel() = MatchStatModel(
    id = matchId,
    statType = statType,
    teamA = teamA.toModel(),
    teamB = teamB.toModel()
)