package com.bl_lia.kirakiratter.domain.interactor.timeline

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.TimelineRepository
import io.reactivex.Single

class UnreblogStatusUseCase(
        private val timelineRepository: TimelineRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Status>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Status> {
        return timelineRepository.unReblog(params[0] as Int)
    }
}