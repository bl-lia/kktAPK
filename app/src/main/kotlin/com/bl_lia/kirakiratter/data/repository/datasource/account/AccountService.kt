package com.bl_lia.kirakiratter.data.repository.datasource.account

import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Relationship
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountService {

    @GET("api/v1/accounts/{id}/statuses")
    fun status(
            @Path("id") id: Int?,
            @Query("max_id") maxId: Int? = null,
            @Query("since_id") sinceId: Int? = null,
            @Query("limit") limit: Int? = null): Single<List<Status>>

    @GET("api/v1/accounts/relationships")
    fun relationships(@Query("id") id: Int?): Single<List<Relationship>>

    @POST("api/v1/accounts/{id}/follow")
    fun follow(@Path("id") id: Int?): Single<Relationship>

    @POST("api/v1/accounts/{id}/unfollow")
    fun unfollow(@Path("id") id: Int?): Single<Relationship>

    @GET("api/v1/accounts/verify_credentials")
    fun verifyCredentials(): Single<Account>

    @GET("api/v1/accounts/{id}")
    fun account(@Path("id") id: Int): Single<Account>
}