package com.bl_lia.kirakiratter.domain.value_object

data class Content(
        val header: String?,
        val body: String?,
        val translatedText: String? = null
)