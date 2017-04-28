package com.bl_lia.kirakiratter.domain.interactor.timeline

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.TimelineRepository
import io.reactivex.Single

class GetSelectedTimelineUseCase(
        private val timelineRepository: TimelineRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<String>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<String> {
        return Single.create { emitter ->
            val timeline: String = timelineRepository.selectedTimeline ?: "none"
            emitter.onSuccess(timeline)
        }
    }
}