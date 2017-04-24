package com.bl_lia.kirakiratter.data.repository.datasource.translation

import com.bl_lia.kirakiratter.domain.value_object.Translation
import io.reactivex.Single

interface TranslationDataStore {

    fun translate(key: String, sourceLang: String, targetLang: String, query: String): Single<List<Translation>>
}