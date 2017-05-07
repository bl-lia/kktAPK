package com.bl_lia.kirakiratter.domain.extension

import android.content.Context
import com.bl_lia.kirakiratter.R

fun Throwable.preparedErrorMessage(context: Context?): String =
        if (localizedMessage.isNullOrEmpty()) {
            "Error"
        } else if(localizedMessage.startsWith("HTTP 520")) {
            context?.resources?.getString(R.string.error_message_5xx) ?: "Error"
        } else {
            localizedMessage
        }
