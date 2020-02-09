package com.foxsportsplayerstats.domain.model

import androidx.annotation.VisibleForTesting

data class TopPlayerStatModel(
    val id: Int,
    val teamId: Int,
    val name: String,
    val position: String,
    val jumperNumber: Int,
    val statValue: Int
)

@VisibleForTesting
fun buildTopPlayer(
    id: Int = -1,
    teamId: Int = -1,
    name: String = "player_name",
    position: String = "position",
    jumperNumber: Int = 99,
    statValue: Int = 0
) = TopPlayerStatModel(
    id = id,
    teamId = teamId,
    name = name,
    position = position,
    jumperNumber = jumperNumber,
    statValue = statValue
)