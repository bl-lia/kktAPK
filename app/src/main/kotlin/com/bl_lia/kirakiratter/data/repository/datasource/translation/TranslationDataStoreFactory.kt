package com.bl_lia.kirakiratter.data.repository.datasource.translation

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TranslationDataStoreFactory
    @Inject constructor(
            @Named("googleCloudClient")
            private val retrofit: Retrofit
    ){

    fun create(): TranslationDataStore {
        val service = retrofit.create(GoogleTranslationService::class.java)
        return ApiTranslationDataStore(service)
    }
}