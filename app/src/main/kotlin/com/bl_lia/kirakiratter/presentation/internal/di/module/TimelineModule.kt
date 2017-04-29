package com.bl_lia.kirakiratter.presentation.internal.di.module

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.interactor.SingleUseCase
import com.bl_lia.kirakiratter.domain.interactor.timeline.GetHomeTimelineUseCase
import com.bl_lia.kirakiratter.domain.interactor.timeline.GetMoreHomeTimelineUseCase
import com.bl_lia.kirakiratter.domain.interactor.timeline.GetMorePublicTimelineUseCase
import com.bl_lia.kirakiratter.domain.interactor.timeline.GetPublicTimelineUseCase
import com.bl_lia.kirakiratter.domain.repository.TimelineRepository
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class TimelineModule {

    @Provides
    @PerFragment
    @Named("getHomeTimeline")
    internal fun provideGetHomeTimeline(
            timelineRepository: TimelineRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return GetHomeTimelineUseCase(timelineRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("getMoreHomeTimeline")
    internal fun provideGetMoreHomeTimeline(
            timelineRepository: TimelineRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return GetMoreHomeTimelineUseCase(timelineRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("getPublicTimeline")
    internal fun provideGetPublicTimeline(
            timelineRepository: TimelineRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return GetPublicTimelineUseCase(timelineRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @PerFragment
    @Named("getMorePublicTimeline")
    internal fun provideGetMorePublicTimeline(
            timelineRepository: TimelineRepository,
            threadExecutor: ThreadExecutor,
            postExecutionThread: PostExecutionThread
    ): SingleUseCase<List<Status>> {
        return GetMorePublicTimelineUseCase(timelineRepository, threadExecutor, postExecutionThread)
    }
}