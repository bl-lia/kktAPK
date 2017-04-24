package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

interface TimelineDataStore {

    fun homeTimeline(): Single<List<Status>>
    fun moreHomeTimeline(maxId: String?, sinceId: String?): Single<List<Status>>

    fun publicTimeline(): Single<List<Status>>
    fun morePublicTimeline(maxId: String?, sinceId: String?): Single<List<Status>>

    fun favourite(id: String): Single<Status>
    fun unfavourite(id: String): Single<Status>

    fun reblog(id: Int): Single<Status>
    fun unReblog(id: Int): Single<Status>
}