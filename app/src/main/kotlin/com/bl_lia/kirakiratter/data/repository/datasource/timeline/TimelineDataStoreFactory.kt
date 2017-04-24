package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineDataStoreFactory
    @Inject constructor(
            private val retrofit: Retrofit
    ) {

    fun create(): TimelineDataStore {
        val service = retrofit.create(TimelineService::class.java)
        return ApiTimelineDataStore(service)
    }
}