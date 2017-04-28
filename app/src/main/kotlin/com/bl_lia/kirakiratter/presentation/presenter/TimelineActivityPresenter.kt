package com.bl_lia.kirakiratter.presentation.presenter

import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

@PerActivity
class TimelineActivityPresenter
    @Inject constructor(
            @Named("getSelectedTimeline")
            private val getSelectedTimeline: SingleUseCase<String>,
            @Named("setSelectedTimeline")
            private val setSelectedTimeline: CompletableUseCase<String>
    ): Presenter {

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getSelectedTimeline(): Single<String> = getSelectedTimeline.execute()

    fun setSelectedTimeline(timeline: String) {
        setSelectedTimeline.execute(timeline)
                .subscribe()
    }
}