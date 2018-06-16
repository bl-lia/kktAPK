package com.bl_lia.kirakiratter.domain.entity.realm

import com.bl_lia.kirakiratter.domain.entity.Status
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmStatus(
        @PrimaryKey
        open var id: String = "",
        open var content: RealmContent? = null,
        open var account: RealmAccount? = null,
        open var reblog: RealmStatus? = null,
        open var reblogged: Boolean = false,
        open var favourited: Boolean = false,
        open var mediaAttachments: RealmList<RealmMedia> = RealmList(),
        open var sensitive: Boolean = false,
        open var createdAt: Date? = null,
        open var url: String? = null
): RealmObject() {

    fun toStatus(): Status =
            Status(
                    id = id,
                    content = content?.toContent(),
                    account = account?.toAccount(),
                    reblog = reblog?.toStatus(),
                    reblogged = reblogged,
                    favourited = favourited,
                    mediaAttachments = mediaAttachments.map { it.toMedia() },
                    sensitive = sensitive,
                    createdAt = createdAt,
                    url = url
            )
}