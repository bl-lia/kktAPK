package com.bl_lia.kirakiratter.domain.repository

import com.bl_lia.kirakiratter.domain.entity.Notification
import io.reactivex.Single

interface NotificationRepository {

    fun list(): Single<List<Notification>>
    fun listMore(maxId: Int? = null, sinceId: Int? = null): Single<List<Notification>>
}