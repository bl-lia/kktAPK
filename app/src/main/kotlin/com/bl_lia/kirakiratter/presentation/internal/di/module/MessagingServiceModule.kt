package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.notification.GetNotificationUseCase
import com.bl_lia.kirakiratter.domain.repository.NotificationRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class MessagingServiceModule {

    @Provides
    @PerFragment
    @Named("getNotification")
    fun getNotification(
            notificationRepository: NotificationRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Notification> {
        return GetNotificationUseCase(notificationRepository, threadExecutor, postExecutionThread)
    }
}