package com.foxsportsplayerstats.data

import com.foxsportsplayerstats.data.network.NetworkModule
import com.foxsportsplayerstats.domain.Repository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class] )
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: FoxSportsRepository): Repository
}