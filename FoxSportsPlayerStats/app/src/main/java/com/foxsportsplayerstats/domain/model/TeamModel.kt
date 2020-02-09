package com.foxsportsplayerstats.domain.model

import androidx.annotation.VisibleForTesting

data class TeamModel(
    val id: Int,
    val code: String,
    val name: String,
    val shortName: String,
    val topPlayerStats: List<TopPlayerStatModel>
)

@VisibleForTesting
fun buildTeam(
    id: Int = -1,
    code: String = "code",
    name: String = "name",
    shortName: String = "short",
    topPlayerStats: List<TopPlayerStatModel> = emptyList()
) = TeamModel(
    id = id, code = code, name = name, shortName = shortName, topPlayerStats = topPlayerStats
)
