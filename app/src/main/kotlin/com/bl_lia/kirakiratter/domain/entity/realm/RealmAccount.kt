package com.bl_lia.kirakiratter.domain.entity.realm

import com.bl_lia.kirakiratter.domain.entity.Account
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmAccount(
        @PrimaryKey
        open var id: Int = -1,
        open var userName: String? = null,
        open var displayName: String? = null,
        open var avatar: String? = null,
        open var header: String? = null,
        open var note: String? = null,
        open var followersCount: Int? = null,
        open var followingCount: Int? = null,
        open var statusesCount: Int? = null
): RealmObject() {

    fun toAccount(): Account =
            Account(
                    id = id,
                    userName = userName,
                    displayName = displayName,
                    avatar = avatar,
                    header = header,
                    note = note,
                    followersCount = followersCount,
                    followingCount = followingCount,
                    statusesCount = statusesCount
            )
}