package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.notification.ListMoreNotificationUseCase
import com.bl_lia.kirakiratter.domain.interactor.notification.ListNotificationUseCase
import com.bl_lia.kirakiratter.domain.repository.NotificationRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NotificationModule {

    @Provides
    @PerFragment
    @Named("listNotification")
    fun list(
            notificationRepository: NotificationRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Notification>> {
        return ListNotificationUseCase(notificationRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("listMoreNotification")
    fun listMore(
            notificationRepository: NotificationRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Notification>> {
        return ListMoreNotificationUseCase(notificationRepository, threadExecutor, postExecutionThread)
    }
}