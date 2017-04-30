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

fun String.containsJapanese(): Boolean {
    val japanese = this.toCharArray().filter { char ->
        when (Character.UnicodeBlock.of(char)) {
            Character.UnicodeBlock.HIRAGANA,
            Character.UnicodeBlock.KATAKANA,
            Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS,
            Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
            Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION -> true
            else -> false
        }
    }

    return japanese.count() > 0
}

