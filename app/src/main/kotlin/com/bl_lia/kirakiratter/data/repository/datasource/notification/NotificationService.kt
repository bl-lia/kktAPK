package com.bl_lia.kirakiratter.data.repository.datasource.notification

import com.bl_lia.kirakiratter.domain.entity.Notification
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {

    @GET("api/v1/notifications")
    fun list(@Query("max_id") maxId: Int? = null,
             @Query("since_id") sinceId: Int? = null,
             @Query("limit") limit: Int? = null): Single<List<Notification>>

    @GET("api/v1/notifications/{id}")
    fun notification(@Path("id") id: Int): Single<Notification>
}