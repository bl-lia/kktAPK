package com.bl_lia.kirakiratter.data.repository.datasource.auth

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials

interface AuthDataStore {

    fun authInfo(): Single<AuthInfo>
    fun reset(authInfo: AuthInfo): Completable

    fun authenticateApp(clientName: String, redirectUri: String, scopes: String, website: String): Single<AppCredentials>
    fun accessToken(code: String): Maybe<AccessToken>

    fun logout(): Completable
}