package com.bl_lia.kirakiratter.domain.entity.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmTimeline(
        @PrimaryKey
        open var scope: String = "",
        open var statusList: RealmList<RealmStatus> = RealmList()
): RealmObject()