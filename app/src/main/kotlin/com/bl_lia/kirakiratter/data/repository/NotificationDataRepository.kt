package com.bl_lia.kirakiratter.data.repository

import com.bl_lia.kirakiratter.data.repository.datasource.notification.NotificationDataStoreFactory
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.repository.NotificationRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDataRepository
    @Inject constructor(
            private val notificationDataStoreFactory: NotificationDataStoreFactory
    ): NotificationRepository {

    override fun list(): Single<List<Notification>> =
            notificationDataStoreFactory.create().list()

    override fun listMore(maxId: Int?, sinceId: Int?): Single<List<Notification>> =
            notificationDataStoreFactory.create().listMore(maxId, sinceId)

    override fun notification(id: Int): Single<Notification> =
            notificationDataStoreFactory.create().notification(id)
}