package com.foxsportsplayerstats.network

import com.foxsportsplayerstats.domain.model.TeamModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamData(
    val id: Int,
    val name: String,
    val code: String,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "top_players") val topPlayers: List<TopPlayerData>
)

fun TeamData.toModel() = TeamModel(
    id = id,
    code = code,
    name = name,
    shortName = shortName,
    topPlayerStats = topPlayers.map { it.toModel(id) }
)