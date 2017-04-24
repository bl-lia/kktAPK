package com.bl_lia.kirakiratter.domain.entity

import java.util.*

data class Notification(
        val id: Int,
        val type: String,
        val createdAt: Date,
        val account: Account?,
        val status: Status?
)