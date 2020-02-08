package com.foxsportsplayerstats.network

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerDetailedStats(
    val id: Int,
    val surname: String,
    val position: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "last_match_stats") val lastMatchStats: Map<String, Int?>
    //@Json(name = "last_match_stats") val lastMatchStats: PlayerMatchStats
) {

    fun getLastMatchStatPairs(): List<Pair<String, Int>> {
        val pairs = arrayListOf<Pair<String, Int>>()
        val iterator = lastMatchStats.iterator()
        while(iterator.hasNext()) {
            val next = iterator.next()
            pairs.add(Pair(next.key, next.value ?: 0))
        }

        Log.d("LastMatch Pairs:", lastMatchStats.toString())

        return pairs
    }
}