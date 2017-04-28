package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.CompletableUseCase
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.timeline.GetSelectedTimelineUseCase
import com.bl_lia.kirakiratter.domain.interactor.timeline.SetSelectedTimelineUseCase
import com.bl_lia.kirakiratter.domain.repository.TimelineRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TimelineActivityModule {

    @Provides
    @PerActivity
    @Named("getSelectedTimeline")
    internal fun provideGetSelectedTimeline(
            timelineRepository: TimelineRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<String> {
        return GetSelectedTimelineUseCase(timelineRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerActivity
    @Named("setSelectedTimeline")
    internal fun provideSetSelectedTimeline(
            timelineRepository: TimelineRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): CompletableUseCase<String> {
        return SetSelectedTimelineUseCase(timelineRepository, threadExecutor, postExecutionThread)
    }
}