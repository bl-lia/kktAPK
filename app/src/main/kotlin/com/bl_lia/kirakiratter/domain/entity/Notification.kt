package com.bl_lia.kirakiratter.domain.entity

import android.content.Context
import android.support.annotation.StringRes
import com.bl_lia.kirakiratter.R
import java.util.*

data class Notification(
        val id: Int,
        val type: String,
        val createdAt: Date,
        val account: Account?,
        val status: Status?
) {
    fun notifiedMessage(context: Context): String? =
            when (type) {
                "reblog" -> {
                    context.s(R.string.notification_boost, account?.preparedDisplayName)
                }
                "favourite" -> {
                    context.s(R.string.notification_favourite, account?.preparedDisplayName)
                }
                "follow" -> {
                    context.s(R.string.notification_follow, account?.preparedDisplayName)
                }
                "mention" -> {
                    context.s(R.string.notification_mention, account?.preparedDisplayName)
                }
                else -> null
            }

    private fun Context.s(@StringRes id: Int, vararg paramString: String?): String = resources.getString(id).format(*paramString)
}