package com.bl_lia.kirakiratter.domain.interactor

import android.util.Log
import com.bl_lia.kirakiratter.BuildConfig
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.crashlytics.android.Crashlytics
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T>(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) {

    protected abstract fun build(params: Array<out Any>): Single<T>

    var processing: Boolean = false
        private set

    fun execute(vararg params: Any): Single<T> {
        processing = true
        return build(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
                .doAfterTerminate {
                    processing = false
                }
                .doOnError { error ->
                    if (BuildConfig.DEBUG) {
                        Log.e("KiraKiratter", "error", error)
                    }
                    Crashlytics.logException(error)
                }
    }
}