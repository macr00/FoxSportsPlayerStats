package com.foxsportsplayerstats

import com.foxsportsplayerstats.domain.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class RxSchedulersTesting(
    private val test: TestScheduler
) : RxSchedulers {

    override val main: Scheduler
        get() = test

    override val io: Scheduler
        get() = test
}