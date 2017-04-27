package com.bl_lia.kirakiratter.data.repository.datasource.push_notification

import io.reactivex.Single

interface PushNotificationDataStore {

    fun register(
            instanceUrl: String,
            accessToken: String,
            deviceToken: String): Single<String>

    fun unregister(
            instanceUrl: String,
            accessToken: String,
            deviceToken: String): Single<String>

    fun isRegistered(
            instanceUrl: String,
            accessToken: String,
            deviceToken: String): Single<Boolean>
}