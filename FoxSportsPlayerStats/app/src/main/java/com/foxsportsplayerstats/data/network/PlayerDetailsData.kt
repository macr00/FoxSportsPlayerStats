package com.foxsportsplayerstats.data.network

import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDetailsData(
    val id: Int,
    val surname: String,
    val position: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "last_match_stats") val lastMatchStats: Map<String, Int?>
)

fun PlayerDetailsData.toModel() = PlayerDetailsModel(
    id = id,
    position = position,
    fullName = fullName,
    surname = surname,
    lastMatchStats = lastMatchStats.toPairs()
)

fun Map<String, Int?>.toPairs(): List<Pair<String, Int>> {
    val pairs = arrayListOf<Pair<String, Int>>()
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        val next = iterator.next()
        pairs.add(Pair(next.key, next.value ?: 0))
    }

    return pairs
}