package com.bl_lia.kirakiratter.domain.value_object

import com.bl_lia.kirakiratter.domain.entity.realm.RealmContent
import java.util.*

data class Content(
        val header: String?,
        val body: String?,
        val translatedText: String? = null
) {
    fun toRealm(): RealmContent =
            RealmContent(
                    id = UUID.randomUUID().toString(),
                    body = body,
                    translatedText = translatedText
            )
}