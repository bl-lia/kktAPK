package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.data.cache.TimelineCache
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single

class DiskTimelineDataStore(
        private val cache: TimelineCache
) : TimelineDataStore {

    override fun homeTimeline(): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moreHomeTimeline(maxId: String?, sinceId: String?): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun publicTimeline(): Single<List<Status>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun reblog(id: Int): Single<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unReblog(id: Int): Single<Status> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var selectedTimeline: String?
        get() = cache.selectedTimeline
        set(value) { cache.selectedTimeline = value }
}