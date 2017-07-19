package com.bl_lia.kirakiratter.data.repository.datasource.config

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigDataStoreFactory
    @Inject constructor(
            private val sharedPreferences: SharedPreferences
    ){

    fun create(): ConfigDataStore = SharedConfConfigDataStore(sharedPreferences)
}