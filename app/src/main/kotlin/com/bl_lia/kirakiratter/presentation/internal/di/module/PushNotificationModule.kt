package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.push_notification.IsRegisteredTokenUseCase
import com.bl_lia.kirakiratter.domain.interactor.push_notification.RegisterTokenUseCase
import com.bl_lia.kirakiratter.domain.interactor.push_notification.UnregisterTokenUseCase
import com.bl_lia.kirakiratter.domain.repository.AuthRepository
import com.bl_lia.kirakiratter.domain.repository.PushNotificationRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class PushNotificationModule {

    @Provides
    @PerFragment
    @Named("registerToken")
    fun registerToken(
            authRepository: AuthRepository,
            pushNotificationRepository: PushNotificationRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<String> {
        return RegisterTokenUseCase(authRepository, pushNotificationRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("unregisterToken")
    fun unregisterToken(
            authRepository: AuthRepository,
            pushNotificationRepository: PushNotificationRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<String> {
        return UnregisterTokenUseCase(authRepository, pushNotificationRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("isRegisteredToken")
    fun isRegisteredToken(
            authRepository: AuthRepository,
            pushNotificationRepository: PushNotificationRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Boolean> {
        return IsRegisteredTokenUseCase(authRepository, pushNotificationRepository, threadExecutor, postExecutionThread)
    }
}