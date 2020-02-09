package com.foxsportsplayerstats.app.di

import com.foxsportsplayerstats.app.RxSchedulersImpl
import com.foxsportsplayerstats.domain.RxSchedulers
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideSchedulers(schedulers: RxSchedulersImpl): RxSchedulers

    @Binds
    abstract fun provideConfig(config: BuildConfiguriation): Config
}