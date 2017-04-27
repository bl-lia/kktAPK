package com.bl_lia.kirakiratter.domain.interactor.notification

import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.repository.NotificationRepository
import io.reactivex.Single

class GetNotificationUseCase(
        private val notificationRepository: NotificationRepository,
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread
) : SingleUseCase<Notification>(threadExecutor, postExecutionThread) {

    override fun build(params: Array<out Any>): Single<Notification> =
            notificationRepository.notification(params[0] as Int)
}