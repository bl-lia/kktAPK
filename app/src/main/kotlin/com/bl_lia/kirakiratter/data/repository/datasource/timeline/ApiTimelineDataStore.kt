package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.data.cache.TimelineStatusCache
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

class ApiTimelineDataStore(
        private val timelineService: TimelineService,
        private val timelineStatusCache: TimelineStatusCache
) : TimelineDataStore {

    override fun homeTimeline(): Single<List<Status>> =
            timelineService.homeTimeline()
                    .doAfterSuccess { list ->
                        timelineStatusCache.reset("home", list)
                    }

    override fun moreHomeTimeline(maxId: String?, sinceId: String?): Single<List<Status>> =
            timelineService.homeTimeline(maxId, sinceId)

    override fun publicTimeline(): Single<List<Status>> =
            timelineService.publicTimeline()
                    .doAfterSuccess { list ->
                        timelineStatusCache.reset("local", list)
                    }

    override fun morePublicTimeline(maxId: String?, sinceId: String?): Single<List<Status>> =
            timelineService.publicTimeline(maxId = maxId, sinceId = sinceId)

    override fun favourite(id: String): Single<Status> =
            timelineService.favourite(id)

    override fun unfavourite(id: String): Single<Status> =
            timelineService.unfavouriteStatus(id)

    override fun reblog(id: String): Single<Status> =
            timelineService.reblog(id)

    override fun unReblog(id: String): Single<Status> =
            timelineService.unReblog(id)

    override var selectedTimeline: String?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
}