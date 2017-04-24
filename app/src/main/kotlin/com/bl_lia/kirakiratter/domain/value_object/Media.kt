package com.bl_lia.kirakiratter.domain.value_object

data class Media(
        val id: Int,
        val type: String,
        val previewUrl: String?,
        val url: String?,
        val textUrl: String?
)