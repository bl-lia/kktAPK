package com.bl_lia.kirakiratter.data.repository.datasource.config

import android.content.SharedPreferences
import io.reactivex.Single

class SharedConfConfigDataStore(
        private val sharedPreferences: SharedPreferences
): ConfigDataStore {

    companion object {
        const val CONFIG_SIMPLE_MODE = "config_simple_mode"
    }

    override fun setSimpleMode(enabled: Boolean): Single<Boolean> =
            Single.create { emitter ->
                sharedPreferences.edit()
                        .apply {
                            putBoolean(CONFIG_SIMPLE_MODE, enabled)
                        }
                        .apply()
                emitter.onSuccess(enabled)
            }

    override fun simpleMode(): Single<Boolean> =
            Single.just(sharedPreferences.getBoolean(CONFIG_SIMPLE_MODE, false))
}