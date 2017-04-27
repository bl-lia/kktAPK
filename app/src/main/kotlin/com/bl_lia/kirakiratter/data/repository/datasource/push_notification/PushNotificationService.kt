package com.bl_lia.kirakiratter.data.repository.datasource.push_notification

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PushNotificationService {

    @FormUrlEncoded
    @POST("/register")
    fun register(
            @Field("instance_url") instanceUrl: String,
            @Field("access_token") accessToken: String,
            @Field("device_token") deviceToken: String): Single<ResponseBody>

    @FormUrlEncoded
    @POST("/unregister")
    fun unregister(
            @Field("instance_url") instanceUrl: String,
            @Field("access_token") accessToken: String,
            @Field("device_token") deviceToken: String): Single<ResponseBody>

    @FormUrlEncoded
    @POST("/registered")
    fun isRegistered(
            @Field("instance_url") instanceUrl: String,
            @Field("access_token") accessToken: String,
            @Field("device_token") deviceToken: String): Single<IsRegisteredResponse>

    data class IsRegisteredResponse(
            val registered: Boolean
    )
}