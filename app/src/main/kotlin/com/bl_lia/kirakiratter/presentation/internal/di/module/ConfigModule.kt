package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.config.GetSimpleModeUseCase
import com.bl_lia.kirakiratter.domain.interactor.config.SetSimpleModeUseCase
import com.bl_lia.kirakiratter.domain.repository.ConfigRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ConfigModule {

    @Provides
    @PerFragment
    @Named("setSimpleMode")
    internal fun provideSetSimpleMode(
            configRepository: ConfigRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Boolean> {
        return SetSimpleModeUseCase(configRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("getSimpleMode")
    internal fun provideGetSimpleMode(
            configRepository: ConfigRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Boolean> {
        return GetSimpleModeUseCase(configRepository, threadExecutor, postExecutionThread)
    }

}