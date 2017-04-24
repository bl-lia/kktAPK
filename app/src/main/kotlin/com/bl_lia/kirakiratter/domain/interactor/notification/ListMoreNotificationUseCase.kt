package com.bl_lia.kirakiratter.domain.interactor.notification

import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.NotificationRepository
import io.reactivex.Single

class ListMoreNotificationUseCase(
        private val notificationRepository: NotificationRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Notification>>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<List<Notification>> {
        val maxId: Int? = params[0] as Int?
        return notificationRepository.listMore(maxId)
    }
}