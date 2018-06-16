package com.bl_lia.kirakiratter.domain.entity

import android.content.Context
import android.text.format.DateUtils
import com.bl_lia.kirakiratter.domain.entity.realm.RealmStatus
import com.bl_lia.kirakiratter.domain.extension.asHtml
import com.bl_lia.kirakiratter.domain.value_object.Content
import com.bl_lia.kirakiratter.domain.value_object.Media
import java.util.*

data class Status(
        val id: String,
        val content: Content?,
        val account: Account?,
        val reblog: Status? = null,
        val reblogged: Boolean,
        val favourited: Boolean,
        val mediaAttachments: List<Media> = listOf(),
        val sensitive: Boolean = false,
        val createdAt: Date? = null,
        val url: String?
) {
    fun debug(): String {
        return "id: %s, header: %s, body: %s".format(id, content?.header, content?.body)
    }

    fun toRealm(): RealmStatus =
            RealmStatus(
                    id = id,
                    content = content?.toRealm(),
                    account = account?.toRealm(),
                    reblog = reblog?.toRealm(),
                    reblogged = reblogged,
                    favourited = favourited,
                    sensitive = sensitive,
                    createdAt = createdAt,
                    url = url
            ).also { realmStatus ->
                mediaAttachments.forEach {
                    realmStatus.mediaAttachments.add(it.toRealm())
                }
            }

    fun toSummarizedText(context:Context): String =
            reblog?.toSummarizedText(context) ?:
                    """
${account?.preparedDisplayName} (${account?.userName})

${if (content?.header.isNullOrEmpty()) "" else content?.header?.plus("\n")}${content?.body?.asHtml()?.trim()}

${createdAt?.time?.let { DateUtils.formatDateTime(context, it, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME) }}
${url ?: ""}"""
}