package com.bl_lia.kirakiratter.domain.entity

import java.io.Serializable

data class Account(
        val id: Int,
        val userName: String? = null,
        val displayName: String? = null,
        val avatar: String? = null,
        val header: String? = null,
        val note: String? = null
): Serializable {

    companion object {
        fun invalidAccount(): Account = Account(-1)
    }

    val preparedDisplayName: String? =
            if (displayName.isNullOrEmpty()) {
                userName
            } else {
                displayName
            }

    val isInvalid: Boolean = id == -1
}