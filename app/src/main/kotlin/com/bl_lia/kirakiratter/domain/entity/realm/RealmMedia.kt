package com.bl_lia.kirakiratter.domain.entity.realm

import com.bl_lia.kirakiratter.domain.value_object.Media
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmMedia(
        @PrimaryKey
        open var id: Int = -1,
        open var type: String? = null,
        open var previewUrl: String? = null,
        open var url: String? = null,
        open var textUrl: String? = null
): RealmObject() {

    fun toMedia(): Media =
            Media(
                    id = id,
                    type = type!!,
                    previewUrl = previewUrl,
                    url = url,
                    textUrl = textUrl
            )
}