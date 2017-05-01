package com.bl_lia.kirakiratter.data.cache

import com.bl_lia.kirakiratter.domain.entity.Status

interface TimelineStatusCache {

    fun get(scope: String): List<Status>
    fun reset(scope: String, list: List<Status>)
}