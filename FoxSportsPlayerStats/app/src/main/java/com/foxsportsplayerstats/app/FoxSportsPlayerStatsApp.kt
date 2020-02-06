package com.foxsportsplayerstats.app

import android.app.Activity
import android.app.Application
import com.foxsportsplayerstats.app.di.AppComponent
import com.foxsportsplayerstats.app.di.ComponentProvider
import com.foxsportsplayerstats.app.di.DaggerAppComponent

class FoxSportsPlayerStatsApp : Application(), ComponentProvider {

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appContext(applicationContext)
            .build()
    }
}

val Activity.injector get() =
    (application as FoxSportsPlayerStatsApp).appComponent