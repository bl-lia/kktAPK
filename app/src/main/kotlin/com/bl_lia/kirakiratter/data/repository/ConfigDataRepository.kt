package com.bl_lia.kirakiratter.data.repository

import com.bl_lia.kirakiratter.data.repository.datasource.config.ConfigDataStoreFactory
import com.bl_lia.kirakiratter.domain.repository.ConfigRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigDataRepository
        @Inject constructor(
                private val configDataStoreFactory: ConfigDataStoreFactory
        ): ConfigRepository {

    override fun simpleMode(): Single<Boolean> = configDataStoreFactory.create().simpleMode()

    override fun setSimpleMode(enabled: Boolean): Single<Boolean> = configDataStoreFactory.create().setSimpleMode(enabled)
}