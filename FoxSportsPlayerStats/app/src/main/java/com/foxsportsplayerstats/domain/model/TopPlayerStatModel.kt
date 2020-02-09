package com.foxsportsplayerstats.domain.model

data class TopPlayerStatModel(
    val id: Int,
    val teamId: Int,
    val name: String,
    val position: String,
    val jumperNumber: String,
    val statValue: String
)