package com.bl_lia.kirakiratter.data.repository.datasource.translation

import com.bl_lia.kirakiratter.domain.value_object.Translation
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleTranslationService {

    @GET("/language/translate/v2")
    fun translate(
            @Query("key")
            key: String,
            @Query("source")
            sourceLang: String,
            @Query("target")
            targetLang: String,
            @Query("q")
            query: String): Single<TranslationResponse>

    data class TranslationResponse(
            val data: TranslationData
    )

    data class TranslationData(
            val translations: List<Translation>
    )
}