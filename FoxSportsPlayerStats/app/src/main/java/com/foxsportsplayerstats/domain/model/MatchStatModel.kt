package com.foxsportsplayerstats.domain.model

data class MatchStatModel(
    val id: String,
    val teamA: TeamModel,
    val teamB: TeamModel,
    val statType: String
)