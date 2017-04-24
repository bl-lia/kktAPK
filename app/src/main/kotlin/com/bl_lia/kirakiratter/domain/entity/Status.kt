package com.bl_lia.kirakiratter.domain.entity

import com.bl_lia.kirakiratter.domain.value_object.Content
import com.bl_lia.kirakiratter.domain.value_object.Media

data class Status(
        val id: Int,
        val content: Content,
        val account: Account?,
        val reblog: Status? = null,
        val reblogged: Boolean,
        val favourited: Boolean,
        val mediaAttachments: List<Media> = listOf(),
        val sensitive: Boolean = false
) {
    fun debug(): String {
        return "id: %s, header: %s, body: %s".format(id, content.header, content.body)
    }
}