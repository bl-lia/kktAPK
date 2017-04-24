package com.bl_lia.kirakiratter.data.repository.datasource.auth

import io.reactivex.Single
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("oauth/token")
    fun fetchOAuthToken(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("redirect_uri") redirectUri: String,
            @Field("code") code: String,
            @Field("grant_type") grantType: String
    ): Single<AccessToken>

    @FormUrlEncoded
    @POST("api/v1/apps")
    fun authenticateApp(
            @Field("client_name") clientName: String,
            @Field("redirect_uris") redirectUri: String,
            @Field("scopes") scopes: String,
            @Field("website") website: String
    ): Single<AppCredentials>

}