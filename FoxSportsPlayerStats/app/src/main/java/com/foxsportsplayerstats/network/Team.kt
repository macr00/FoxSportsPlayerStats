package com.foxsportsplayerstats.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Team(
    val id: Int,
    val name: String,
    val code: String,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "top_players") val topPlayers: List<TopPlayer>
)