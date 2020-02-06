package com.foxsportsplayerstats.app

import com.foxsportsplayerstats.domain.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RxSchedulersImpl
@Inject
constructor() : RxSchedulers {

    override val main: Scheduler
        get() = AndroidSchedulers.mainThread()

    override val io: Scheduler
        get() = Schedulers.io()
}