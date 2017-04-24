package com.bl_lia.kirakiratter.presentation

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UiThread
    @Inject constructor(): PostExecutionThread {

    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}