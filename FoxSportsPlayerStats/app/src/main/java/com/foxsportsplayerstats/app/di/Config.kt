package com.foxsportsplayerstats.app.di

import com.foxsportsplayerstats.BuildConfig
import javax.inject.Inject

interface Config {

    fun isDebug(): Boolean
}

class BuildConfiguriation
@Inject
constructor(): Config {

    override fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}