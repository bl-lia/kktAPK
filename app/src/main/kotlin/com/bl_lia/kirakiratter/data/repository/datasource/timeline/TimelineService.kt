package com.bl_lia.kirakiratter.data.repository.datasource.timeline

import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TimelineService {

    @GET("api/v1/timelines/home")
    fun homeTimeline(
            @Query("max_id") maxId: String? = null,
            @Query("since_id") sinceId: String? = null,
            @Query("limit") limit: Int? = null): Single<List<Status>>

    @GET("api/v1/timelines/public")
    fun publicTimeline(
            @Query("local") local: Boolean = true,
            @Query("max_id") maxId: String? = null,
            @Query("since_id") sinceId: String? = null,
            @Query("limit") limit: Int? = null): Single<List<Status>>


    // favouriting/unfavouriting

    @POST("api/v1/statuses/{id}/favourite")
    fun favourite(@Path("id") id: String): Single<Status>

    @POST("api/v1/statuses/{id}/unfavourite")
    fun unfavouriteStatus(@Path("id") id: String): Single<Status>

    // reblog/unreblog

    @POST("api/v1/statuses/{id}/reblog")
    fun reblog(@Path("id") id: String): Single<Status>

    @POST("api/v1/statuses/{id}/unreblog")
    fun unReblog(@Path("id") id: String): Single<Status>
}