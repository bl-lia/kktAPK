package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

class ApiTimelineDataStore(
        private val timelineService: TimelineService
) : TimelineDataStore {

    override fun homeTimeline(): Single<List<Status>> =
            timelineService.homeTimeline()

    override fun moreHomeTimeline(maxId: String?, sinceId: String?): Single<List<Status>> =
            timelineService.homeTimeline(maxId, sinceId)

    override fun publicTimeline(): Single<List<Status>> =
            timelineService.publicTimeline()

    override fun morePublicTimeline(maxId: String?, sinceId: String?): Single<List<Status>> =
            timelineService.publicTimeline(maxId = maxId, sinceId = sinceId)

    override fun favourite(id: String): Single<Status> =
            timelineService.favourite(id)

    override fun unfavourite(id: String): Single<Status> =
            timelineService.unfavouriteStatus(id)

    override fun reblog(id: Int): Single<Status> =
            timelineService.reblog(id)

    override fun unReblog(id: Int): Single<Status> =
            timelineService.unReblog(id)
}