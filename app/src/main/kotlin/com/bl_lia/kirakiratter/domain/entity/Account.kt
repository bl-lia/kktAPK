package com.bl_lia.kirakiratter.domain.entity

import android.text.Spanned
import java.io.Serializable

data class Account(
        val id: Int,
        val userName: String?,
        val displayName: String?,
        val avatar: String?,
        val header: String?,
        val note: String?
): Serializable {

    val preparedDisplayName: String? =
            if (displayName.isNullOrEmpty()) {
                userName
            } else {
                displayName
            }
}