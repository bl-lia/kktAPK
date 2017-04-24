package com.bl_lia.kirakiratter.domain.value_object

import android.text.Spanned

data class Content(
        val header: String?,
        val body: Spanned?,
        val translatedText: String? = null
)