package com.bl_lia.kirakiratter.domain.repository

import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

interface TimelineRepository {

    fun homeTimeline(): Single<List<Status>>
    fun moreHomeTimeline(maxId: String? = null, sinceId: String? = null): Single<List<Status>>

    fun publicTimeline(): Single<List<Status>>
    fun morePublicTimeline(maxId: String? = null, sinceId: String? = null): Single<List<Status>>

    fun favourite(id: String): Single<Status>
    fun unfavourite(id: String): Single<Status>

    fun reblog(id: Int): Single<Status>
    fun unReblog(id: Int): Single<Status>

    var selectedTimeline: String?
}