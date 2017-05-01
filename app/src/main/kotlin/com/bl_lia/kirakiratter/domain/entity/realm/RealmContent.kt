package com.bl_lia.kirakiratter.domain.entity.realm

import com.bl_lia.kirakiratter.domain.value_object.Content
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmContent(
        @PrimaryKey
        open var id: String = "",
        open var header: String? = null,
        open var body: String? = null,
        open var translatedText: String? = null
): RealmObject() {

    fun toContent(): Content =
            Content(
                    header = header,
                    body = body,
                    translatedText = translatedText
            )
}