package com.bl_lia.kirakiratter.domain.interactor.timeline

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.TimelineRepository
import io.reactivex.Single

class GetNewHomeTimelineUseCase(
        private val timelineRepository: TimelineRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Status>>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<List<Status>> = timelineRepository.newHomeTimeline()
}