package com.foxsportsplayerstats.domain.model

import androidx.annotation.VisibleForTesting

data class PlayerDetailsModel(
    val id: Int,
    val surname: String,
    val fullName: String,
    val position: String,
    val lastMatchStats: List<Pair<String, Int>>
)

@VisibleForTesting
fun buildPlayerDetailsModel(
    id: Int = -1,
    surname: String = "Surname",
    fullName: String = "Full Name",
    position: String = "Position",
    lastMatchStats: List<Pair<String, Int>> = emptyList()
) = PlayerDetailsModel(
    id = id,
    surname = surname,
    fullName = fullName,
    position = position,
    lastMatchStats = lastMatchStats
)