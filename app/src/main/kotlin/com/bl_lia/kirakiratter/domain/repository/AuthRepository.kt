package com.bl_lia.kirakiratter.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials

interface AuthRepository {

    fun authInfo(): Single<AuthInfo>
    fun authenticateApp(clientName: String, redirectUri: String, scopes: String, website: String): Single<AppCredentials>

    fun reset(authInfo: AuthInfo): Completable

    fun accessToken(code: String): Single<AccessToken>
    fun cachedAccessToken(): Single<AccessToken>

    fun isAuthenticated(): Single<Boolean>

    fun logout(): Completable
}