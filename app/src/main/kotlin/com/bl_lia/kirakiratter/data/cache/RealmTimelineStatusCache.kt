package com.bl_lia.kirakiratter.data.cache

import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.entity.realm.RealmTimeline
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class RealmTimelineStatusCache
    @Inject constructor(
            private val realmConfiguration: RealmConfiguration
    ): TimelineStatusCache {

    override fun get(scope: String): List<Status> {
        Realm.getInstance(realmConfiguration).use { realm ->
            return realm.where(RealmTimeline::class.java)
                    .equalTo("scope", scope)
                    .findFirst()
                    ?.statusList
                    ?.map { it.toStatus() }
                    .orEmpty()

        }
    }

    override fun reset(scope: String, list: List<Status>) {
        Realm.getInstance(realmConfiguration).use { realm ->
            realm.executeTransaction {
                realm.where(RealmTimeline::class.java)
                        .equalTo("scope", scope)
                        .findAll()
                        .deleteAllFromRealm()

                val timeline = RealmTimeline(scope = scope).also { realmTimeline ->
                    list.forEach { status ->
                        realmTimeline.statusList.add(status.toRealm())
                    }
                }
                realm.copyToRealmOrUpdate(timeline)
            }
        }
    }
}