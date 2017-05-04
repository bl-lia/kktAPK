package com.bl_lia.kirakiratter.domain.value_object

data class StatusForm(
        val status: String,
        val inReplyToId: Int? = null,
        val mediaIds: List<Int> = listOf(),
        val sensitive: Boolean = false,
        val spoilerText: String? = null) {

    fun debug(): String =
        "status: %s, spoilerText: %s, mediaId: %s".format(status, spoilerText, mediaIds.joinToString())
}