package com.bl_lia.kirakiratter.data.repository

import com.bl_lia.kirakiratter.data.repository.datasource.push_notification.PushNotificationDataStoreFactory
import com.bl_lia.kirakiratter.domain.repository.PushNotificationRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PushNotificationDataRepository
    @Inject constructor(
            private val factory: PushNotificationDataStoreFactory
    ): PushNotificationRepository {

    override fun register(pushApiUrl: String, instanceUrl: String, accessToken: String, deviceToken: String): Single<String> =
            factory.create(pushApiUrl).register(instanceUrl, accessToken, deviceToken)

    override fun unregister(pushApiUrl: String, instanceUrl: String, accessToken: String, deviceToken: String): Single<String> =
            factory.create(pushApiUrl).unregister(instanceUrl, accessToken, deviceToken)

    override fun isRegistered(pushApiUrl: String, instanceUrl: String, accessToken: String, deviceToken: String): Single<Boolean> =
            factory.create(pushApiUrl).isRegistered(instanceUrl, accessToken, deviceToken)
}