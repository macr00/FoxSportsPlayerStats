package com.foxsportsplayerstats.data.network

import androidx.annotation.VisibleForTesting
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

@VisibleForTesting
fun buildTeamData(
    id: Int = -1,
    name: String = "team_name",
    code: String = "team_code",
    shortName: String = "team_short_name",
    topPlayers: List<TopPlayerData> = emptyList()
) = TeamData(
    id = id, name = name, code = code, shortName = shortName, topPlayers = topPlayers
)