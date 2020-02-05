package com.foxsportsplayerstats.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerMatchStats(
    val errors: Int,
    val goals: Int,
    val intercepted: Int,
    val intercepts: Int,
    val kicks: Int,
    val points: Int,
    val possessions: Int,
    val runs: Int,
    val tackles: Int,
    val tries: Int,
    @Json(name = "mins_played") val minsPlayed: Int
)