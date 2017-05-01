package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.data.cache.TimelineCache
import com.bl_lia.kirakiratter.data.cache.TimelineStatusCache
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineDataStoreFactory
    @Inject constructor(
            private val retrofit: Retrofit,
            private val timelineCache: TimelineCache,
            private val timelineStatusCache: TimelineStatusCache
    ) {

    fun create(): TimelineDataStore = createApi()

    fun createDisk(): DiskTimelineDataStore {
        return DiskTimelineDataStore(timelineCache, timelineStatusCache)
    }

    fun createApi(): ApiTimelineDataStore {
        val service = retrofit.create(TimelineService::class.java)
        return ApiTimelineDataStore(service, timelineStatusCache)
    }
}