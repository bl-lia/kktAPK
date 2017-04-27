package com.bl_lia.kirakiratter.data.repository.datasource.push_notification

import io.reactivex.Single

class ApiPushNotificationDataStore(
        private val service: PushNotificationService
) : PushNotificationDataStore {

    override fun register(instanceUrl: String, accessToken: String, deviceToken: String): Single<String> =
            service.register(instanceUrl, accessToken, deviceToken)
                    .map { body ->
                        body.string()
                    }

    override fun unregister(instanceUrl: String, accessToken: String, deviceToken: String): Single<String> =
            service.unregister(instanceUrl, accessToken, deviceToken)
                    .map { body ->
                        body.string()
                    }

    override fun isRegistered(instanceUrl: String, accessToken: String, deviceToken: String): Single<Boolean> =
            service.isRegistered(instanceUrl, accessToken, deviceToken)
                    .map { it.registered }
}