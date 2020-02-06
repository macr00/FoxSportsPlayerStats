package com.foxsportsplayerstats.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopPlayer(
    val id: Int,
    val position: String,
    @Json(name = "long_name") val longName: String?,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "stat_value") val statValue : String,
    @Json(name = "jumper_number") val jumperNumber: String
)