package com.bl_lia.kirakiratter.domain.extension

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.asHtml(): Spanned? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(this)
        }
