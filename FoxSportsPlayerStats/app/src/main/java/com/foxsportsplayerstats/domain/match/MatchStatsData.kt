package com.foxsportsplayerstats.domain.match

import com.foxsportsplayerstats.network.MatchStat

data class MatchStatsData(
    val matchStats: List<MatchStat>
)