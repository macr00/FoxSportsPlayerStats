package com.foxsportsplayerstats.domain.model

data class PlayerDetailsModel(
    val id: Int,
    val surname: String,
    val fullName: String,
    val position: String,
    val lastMatchStats: List<Pair<String, Int>>
)