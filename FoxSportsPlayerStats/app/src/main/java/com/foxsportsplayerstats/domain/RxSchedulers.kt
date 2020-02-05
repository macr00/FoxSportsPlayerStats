package com.foxsportsplayerstats.domain

import io.reactivex.Scheduler

interface RxSchedulers {
    val main: Scheduler
    val io: Scheduler
}