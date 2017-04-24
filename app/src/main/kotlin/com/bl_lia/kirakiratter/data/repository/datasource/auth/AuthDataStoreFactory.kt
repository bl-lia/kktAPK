package com.bl_lia.kirakiratter.data.repository.datasource.auth

import com.bl_lia.kirakiratter.data.cache.AuthCache
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthDataStoreFactory
    @Inject constructor(
            private val retrofit: Retrofit,
            private val cache: AuthCache,
            @Named("oauthRedirectUri")
            private val oauthRedirectUri: String
    ) {

    enum class Type{
        AuthInfo, AppCredentials, AccessToken
    }

    fun create(type: Type): AuthDataStore =
            when (type) {
                Type.AuthInfo -> {
                    if (cache.authInfo() != null) {
                        createDisk()
                    } else {
                        createApi()
                    }
                }
                Type.AppCredentials -> {
                    if (cache.appCredentials() != null) {
                        createDisk()
                    } else {
                        createApi()
                    }
                }
                Type.AccessToken -> {
                    if (cache.accessToken() != null) {
                        createDisk()
                    } else {
                        createApi()
                    }
                }
            }

    fun createApi(): ApiAuthDataStore {
        val service = retrofit.create(AuthService::class.java)
        return ApiAuthDataStore(service, cache, oauthRedirectUri)
    }

    fun createDisk(): DiskAuthDataStore {
        return DiskAuthDataStore(cache)
    }

}