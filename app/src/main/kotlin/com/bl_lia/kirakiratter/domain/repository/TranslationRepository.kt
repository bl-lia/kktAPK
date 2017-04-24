package com.bl_lia.kirakiratter.domain.repository

import com.bl_lia.kirakiratter.domain.value_object.Translation
import io.reactivex.Single

interface TranslationRepository {
    fun translate(key: String, sourceLang: String, targetLang: String, query: String): Single<List<Translation>>
}