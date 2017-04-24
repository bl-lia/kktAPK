package com.bl_lia.kirakiratter.data.repository.datasource.auth

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import com.bl_lia.kirakiratter.data.cache.AuthCache
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials

class DiskAuthDataStore(
        private val cache: AuthCache
) : AuthDataStore {

    override fun authInfo(): Single<AuthInfo> {
        if (cache.authInfo() != null) {
            return Single.just(cache.authInfo())
        } else {
            return Single.error(Exception("no AuthInfo"))
        }
    }

    override fun reset(authInfo: AuthInfo): Completable =
            Completable.create { emitter ->
                cache.reset(authInfo)
                emitter.onComplete()
            }

    override fun authenticateApp(clientName: String, redirectUri: String, scopes: String, website: String): Single<AppCredentials> =
            Single.just(cache.appCredentials())

    override fun accessToken(code: String): Maybe<AccessToken> {
        val accessToken = cache.accessToken()
        if (accessToken == null) {
            return Maybe.empty()
        }

        return Maybe.just(accessToken)
    }

    override fun logout(): Completable =
            Completable.create { emitter ->
                cache.clear()
                emitter.onComplete()
            }
}