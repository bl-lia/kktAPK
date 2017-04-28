package com.bl_lia.kirakiratter.domain.interactor.timeline

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.repository.TimelineRepository
import io.reactivex.Completable

class SetSelectedTimelineUseCase(
        private val timelineRepository: TimelineRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : CompletableUseCase<String>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Completable {
        return Completable.create { emitter ->
            timelineRepository.selectedTimeline = params[0] as String?
            emitter.onComplete()
        }
    }
}