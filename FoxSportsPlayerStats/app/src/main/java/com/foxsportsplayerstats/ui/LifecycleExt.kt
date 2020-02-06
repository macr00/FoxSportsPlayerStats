package com.foxsportsplayerstats.ui

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable

fun LifecycleOwner.onDestroyObservable(): Observable<Unit> {
    return Observable.create { emitter ->
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            emitter.onNext(Unit)
            emitter.onComplete()
            return@create
        }
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                Log.d("Lifecycle", "onDestroy")
                if (!emitter.isDisposed) {
                    emitter.onNext(Unit)
                    emitter.onComplete()
                }
            }
        })
    }
}