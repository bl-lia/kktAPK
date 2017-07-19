package com.bl_lia.kirakiratter.data.repository.datasource.config

import io.reactivex.Single

interface ConfigDataStore {

    fun setSimpleMode(enabled: Boolean): Single<Boolean>
    fun simpleMode(): Single<Boolean>
}