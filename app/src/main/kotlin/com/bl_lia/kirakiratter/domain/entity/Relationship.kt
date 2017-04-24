package com.bl_lia.kirakiratter.domain.entity

data class Relationship(
        val id: Int,
        val following: Boolean,
        val followedBy: Boolean
)