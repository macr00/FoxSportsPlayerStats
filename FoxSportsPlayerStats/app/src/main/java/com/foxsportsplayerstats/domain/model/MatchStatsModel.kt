package com.foxsportsplayerstats.domain.model

import com.foxsportsplayerstats.network.MatchStat

data class MatchStatsModel(
    val matchStats: List<MatchStat>
)