package com.foxsportsplayerstats.domain.model

data class TeamModel(
    val id: Int,
    val code: String,
    val name: String,
    val shortName: String,
    val topPlayerStats: List<TopPlayerStatModel>
)