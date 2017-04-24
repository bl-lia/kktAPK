package com.bl_lia.kirakiratter.data.repository.datasource.notification

import com.bl_lia.kirakiratter.domain.entity.Notification
import io.reactivex.Single

class ApiNotificationDataStore(
        private val notificationService: NotificationService
) : NotificationDataStore {

    override fun list(): Single<List<Notification>> =
            notificationService.list()

    override fun listMore(maxId: Int?, sinceId: Int?): Single<List<Notification>> =
            notificationService.list(maxId, sinceId)
}