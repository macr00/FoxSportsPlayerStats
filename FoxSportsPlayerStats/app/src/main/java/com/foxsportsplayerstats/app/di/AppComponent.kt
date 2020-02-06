package com.foxsportsplayerstats.app.di

import android.content.Context
import com.foxsportsplayerstats.ui.match.MatchStatsViewModel
import com.foxsportsplayerstats.ui.player.PlayerStatsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun matchStatsViewModelFactory(): ViewModelFactory<MatchStatsViewModel>

    fun playerStatsViewModelFactory(): ViewModelFactory<PlayerStatsViewModel>
}