package com.bl_lia.kirakiratter.data.repository.datasource.notification

import com.bl_lia.kirakiratter.domain.entity.Notification
import io.reactivex.Single

interface NotificationDataStore {

    fun list(): Single<List<Notification>>
    fun listMore(maxId: Int?, sinceId: Int?): Single<List<Notification>>
}