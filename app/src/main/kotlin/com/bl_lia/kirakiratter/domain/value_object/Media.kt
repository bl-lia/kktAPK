package com.bl_lia.kirakiratter.domain.value_object

import com.bl_lia.kirakiratter.domain.entity.realm.RealmMedia

data class Media(
        val id: Int,
        val type: String,
        val previewUrl: String?,
        val url: String?,
        val textUrl: String?
) {

    fun toRealm(): RealmMedia =
            RealmMedia(
                    id = id,
                    type = type,
                    previewUrl = previewUrl,
                    url = url,
                    textUrl = textUrl
            )
}