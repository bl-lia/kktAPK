package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.data.cache.TimelineCache
import com.bl_lia.kirakiratter.data.cache.TimelineStatusCache
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

class DiskTimelineDataStore(
        private val cache: TimelineCache,
        private val timelineStatusCache: TimelineStatusCache
) : TimelineDataStore {

    override fun homeTimeline(): Single<List<Status>> =
            Single.create { emitter ->
                val list = timelineStatusCache.get("home")
                emitter.onSuccess(list)
            }

    override fun moreHomeTimeline(maxId: String?, sinceId: String?): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun publicTimeline(): Single<List<Status>> =
            Single.create { emitter ->
                val list = timelineStatusCache.get("local")
                emitter.onSuccess(list)
            }

    override fun morePublicTimeline(maxId: String?, sinceId: String?): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favourite(id: String): Single<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfavourite(id: String): Single<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reblog(id: String): Single<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unReblog(id: String): Single<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var selectedTimeline: String?
        get() = cache.selectedTimeline
        set(value) { cache.selectedTimeline = value }
}