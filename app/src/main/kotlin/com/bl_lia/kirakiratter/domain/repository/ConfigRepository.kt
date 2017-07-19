package com.bl_lia.kirakiratter.domain.repository

import io.reactivex.Single

interface ConfigRepository {

    fun simpleMode(): Single<Boolean>

    fun setSimpleMode(enabled: Boolean): Single<Boolean>
}