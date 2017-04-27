package com.bl_lia.kirakiratter.domain.repository

import io.reactivex.Single

interface PushNotificationRepository {

    fun register(
            pushApiUrl: String,
            instanceUrl: String,
            accessToken: String,
            deviceToken: String): Single<String>

    fun unregister(
            pushApiUrl: String,
            instanceUrl: String,
            accessToken: String,
            deviceToken: String): Single<String>

    fun isRegistered(
            pushApiUrl: String,
            instanceUrl: String,
            accessToken: String,
            deviceToken: String): Single<Boolean>
}