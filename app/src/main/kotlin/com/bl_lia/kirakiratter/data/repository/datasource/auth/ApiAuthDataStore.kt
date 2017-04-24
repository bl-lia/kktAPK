package com.bl_lia.kirakiratter.data.repository.datasource.auth

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import com.bl_lia.kirakiratter.data.cache.AuthCache
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials

class ApiAuthDataStore(
        private val authService: AuthService,
        private val authCache: AuthCache,
        private val redirectUri: String
) : AuthDataStore {

    override fun authInfo(): Single<AuthInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reset(authInfo: AuthInfo): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun authenticateApp(clientName: String, redirectUri: String, scopes: String, website: String): Single<AppCredentials> {
        return authService
                .authenticateApp(clientName, redirectUri, scopes, website)
                .doOnSuccess { credentials ->
                    authCache.reset(credentials)
                }
    }

    override fun accessToken(code: String): Maybe<AccessToken> {
        val clientId = authCache.appCredentials()?.clientId
        val clientSecret = authCache.appCredentials()?.clientSecret
        if (clientId == null || clientSecret == null) {
            return Maybe.error(Exception("no clientId or clientSecret"))
        }

        return authService
                .fetchOAuthToken(clientId, clientSecret, redirectUri, code, "authorization_code")
                .doOnSuccess { accessToken ->
                    authCache.reset(accessToken)
                }
                .toMaybe()
    }

    override fun logout(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}