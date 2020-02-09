package com.foxsportsplayerstats.domain.model

import androidx.annotation.VisibleForTesting

data class MatchStatModel(
    val id: String,
    val teamA: TeamModel,
    val teamB: TeamModel,
    val statType: String
)

@VisibleForTesting
fun buildMatchStat(
    id: String = "match_stat_id",
    teamA: TeamModel = buildTeam(),
    teamB: TeamModel = buildTeam(),
    statType: String = "stat_type"
) = MatchStatModel(
    id = id, teamA = teamA, teamB = teamB, statType = statType
)