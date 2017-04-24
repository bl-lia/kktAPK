package com.bl_lia.kirakiratter.data.repository

import com.bl_lia.kirakiratter.data.repository.datasource.translation.TranslationDataStoreFactory
import com.bl_lia.kirakiratter.domain.repository.TranslationRepository
import com.bl_lia.kirakiratter.domain.value_object.Translation
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationDataRepository
    @Inject constructor(
            private val factory: TranslationDataStoreFactory
    ): TranslationRepository {

    override fun translate(key: String, sourceLang: String, targetLang: String, query: String): Single<List<Translation>> {
        return factory.create().translate(key, sourceLang, targetLang, query)
    }
}