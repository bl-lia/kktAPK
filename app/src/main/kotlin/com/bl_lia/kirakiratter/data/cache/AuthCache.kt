package com.bl_lia.kirakiratter.data.cache

import com.bl_lia.kirakiratter.domain.entity.AuthInfo
import com.bl_lia.kirakiratter.domain.value_object.AccessToken
import com.bl_lia.kirakiratter.domain.value_object.AppCredentials

interface AuthCache {

    fun authInfo(): AuthInfo?
    fun appCredentials(): AppCredentials?
    fun accessToken(): AccessToken?

    fun reset(authInfo: AuthInfo)
    fun reset(credentials: AppCredentials)
    fun reset(accessToken: AccessToken)

    fun clear()

}