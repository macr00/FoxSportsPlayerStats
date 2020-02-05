package com.foxsportsplayerstats.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDetailedStats(
    val id: Int,
    val surname: String,
    val position: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "last_match_stats") val lastMatchStats: PlayerMatchStats
)