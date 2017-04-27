package com.bl_lia.kirakiratter.data.repository

import com.bl_lia.kirakiratter.data.repository.datasource.auth.AuthDataStoreFactory
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.repository.AuthRepository
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataRepository
        @Inject constructor(
                private val authDataStoreFactory: AuthDataStoreFactory
        ): AuthRepository {

    override fun authInfo(): Single<AuthInfo> =
            authDataStoreFactory.create(AuthDataStoreFactory.Type.AuthInfo).authInfo()

    override fun authenticateApp(clientName: String, redirectUri: String, scopes: String, website: String): Single<AppCredentials> =
            authDataStoreFactory.create(AuthDataStoreFactory.Type.AppCredentials).authenticateApp(clientName, redirectUri, scopes, website)

    override fun reset(authInfo: AuthInfo): Completable =
            authDataStoreFactory.create(AuthDataStoreFactory.Type.AuthInfo).reset(authInfo)

    override fun accessToken(code: String): Single<AccessToken> =
            authDataStoreFactory.create(AuthDataStoreFactory.Type.AccessToken).accessToken(code).toSingle()

    override fun cachedAccessToken(): Single<AccessToken> =
            authDataStoreFactory.createDisk().accessToken("").toSingle()

    override fun isAuthenticated(): Single<Boolean> =
            authDataStoreFactory.createDisk().accessToken("").map { accessToken -> accessToken != null }.defaultIfEmpty(false).toSingle()

    override fun logout(): Completable =
            authDataStoreFactory.createDisk().logout()
}