package com.bl_lia.kirakiratter.data.repository.datasource.status

import android.app.Application
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusDataStoreFactory
    @Inject constructor(
            private val application: Application,
            private val retrofit: Retrofit
    ) {

    fun create(): StatusDataStore {
        val service = retrofit.create(StatusService::class.java)
        return ApiStatusDataStore(application, service)
    }
}