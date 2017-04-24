package com.bl_lia.kirakiratter.data.cache

import android.content.SharedPreferences
import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials
import javax.inject.Inject

class SharedPrefAuthCache
    @Inject constructor(
            private val sharedPreferences: SharedPreferences
    ): AuthCache {

    override fun authInfo(): AuthInfo? {
        val clientId = sharedPreferences.getString("clientId", "")
        val clientSecret = sharedPreferences.getString("clientSecret", "")

        if (clientId.isNotEmpty() && clientSecret.isNotEmpty()) {
            return AuthInfo(clientId, clientSecret)
        } else {
            return null
        }
    }

    override fun appCredentials(): AppCredentials? {
        val clientId = sharedPreferences.getString("clientId", "")
        val clientSecret = sharedPreferences.getString("clientSecret", "")

        if (clientId.isNotEmpty() && clientSecret.isNotEmpty()) {
            return AppCredentials("cache", clientId, clientSecret)
        } else {
            return null
        }
    }

    override fun accessToken(): AccessToken? {
        val accessToken = sharedPreferences.getString("accessToken", "")

        if (accessToken.isNotEmpty()) {
            return AccessToken("cache", accessToken)
        } else {
            return null
        }
    }

    override fun reset(authInfo: AuthInfo) {
        val edit = sharedPreferences.edit()
        edit.putString("clientId", authInfo.clientId)
        edit.putString("clientSecret", authInfo.clientSecret)
        edit.apply()
    }

    override fun reset(credentials: AppCredentials) {
        val edit = sharedPreferences.edit()
        edit.putString("clientId", credentials.clientId)
        edit.putString("clientSecret", credentials.clientSecret)
        edit.apply()
    }

    override fun reset(accessToken: AccessToken) {
        val edit = sharedPreferences.edit()
        edit.putString("accessToken", accessToken.accessToken)
        edit.apply()
    }

    override fun clear() {
        val edit = sharedPreferences.edit()
        edit.remove("clientId")
        edit.remove("clientSecret")
        edit.remove("accessToken")
        edit.apply()
    }
}