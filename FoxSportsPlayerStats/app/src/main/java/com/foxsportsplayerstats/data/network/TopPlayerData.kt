package com.foxsportsplayerstats.data.network

import com.foxsportsplayerstats.domain.model.TopPlayerStatModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopPlayerData(
    val id: Int,
    val position: String,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "stat_value") val statValue : Int,
    @Json(name = "jumper_number") val jumperNumber: Int
)

fun TopPlayerData.toModel(teamId: Int) = TopPlayerStatModel(
    id = id,
    teamId = teamId,
    name = shortName,
    position = position,
    statValue = statValue,
    jumperNumber = jumperNumber
)