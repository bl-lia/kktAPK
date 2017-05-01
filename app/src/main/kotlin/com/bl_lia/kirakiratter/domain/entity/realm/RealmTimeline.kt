package com.bl_lia.kirakiratter.domain.entity.realm

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmTimeline(
        @PrimaryKey
        open var scope: String = "",
        open var statusList: RealmList<RealmStatus> = RealmList(),
        open var created: Date = Date()
): RealmObject()