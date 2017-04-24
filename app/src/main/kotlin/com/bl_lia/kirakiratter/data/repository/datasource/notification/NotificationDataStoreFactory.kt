package com.bl_lia.kirakiratter.data.repository.datasource.notification

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDataStoreFactory
    @Inject constructor(
            private val retrofit: Retrofit
    ){

    fun create(): NotificationDataStore {
        val service = retrofit.create(NotificationService::class.java)
        return ApiNotificationDataStore(service)
    }
}