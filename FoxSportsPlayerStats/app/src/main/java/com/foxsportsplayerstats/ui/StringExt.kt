package com.foxsportsplayerstats.ui

fun String.capitalizeWords(): String =
    split("_").joinToString(" ") { it.capitalize() }
