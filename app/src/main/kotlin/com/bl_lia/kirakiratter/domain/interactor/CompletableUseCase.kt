package com.bl_lia.kirakiratter.domain.interactor

import android.util.Log
import com.bl_lia.kirakiratter.BuildConfig
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.crashlytics.android.Crashlytics

abstract class CompletableUseCase<T>(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun build(params: Array<out Any>): Completable

    fun execute(vararg params: Any): Completable {
        return build(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .doOnError { error ->
                    if (BuildConfig.DEBUG) {
                        Log.e("KiraKiratter", "error", error)
                    }
                    Crashlytics.logException(error)
                }
    }
}