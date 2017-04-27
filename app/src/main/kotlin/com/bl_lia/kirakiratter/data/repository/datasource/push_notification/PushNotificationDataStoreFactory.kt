package com.bl_lia.kirakiratter.data.repository.datasource.push_notification

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PushNotificationDataStoreFactory
    @Inject constructor(
            @Named("PushRetrofit")
            private val retrofit: Retrofit
    ){

    fun create(pushApiUrl: String): PushNotificationDataStore {
        val newRetrofit = retrofit.newBuilder()
                .baseUrl(pushApiUrl)
                .build()
        val service = newRetrofit.create(PushNotificationService::class.java)
        return ApiPushNotificationDataStore(service)
    }


}