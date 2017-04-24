package com.bl_lia.kirakiratter.presentation.internal.di.module

import dagger.Module
import dagger.Provides
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.status.PostStatusUseCase
import com.bl_lia.kirakiratter.domain.interactor.status.UploadMediaUseCase
import com.bl_lia.kirakiratter.domain.repository.StatusRepository
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import javax.inject.Named

@Module
class StatusModule {

    @Provides
    @PerFragment
    @Named("postStatus")
    fun providePostStatus(
            statusRepository: StatusRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Status> {
        return PostStatusUseCase(statusRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("uploadMedia")
    fun provideUploadMedia(
            statusRepository: StatusRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<Media> {
        return UploadMediaUseCase(statusRepository, threadExecutor, postExecutionThread)
    }
}