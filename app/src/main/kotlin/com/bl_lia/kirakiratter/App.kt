package com.bl_lia.kirakiratter

import android.support.multidex.MultiDexApplication
import com.bl_lia.kirakiratter.presentation.internal.di.HasComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.ApplicationComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerApplicationComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.ApplicationModule
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import io.fabric.sdk.android.Fabric
import io.realm.Realm

class App : MultiDexApplication(), HasComponent<ApplicationComponent> {

    private val _component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    private val fabric: Fabric by lazy {
        Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build()
    }

    override val component: ApplicationComponent
        get() = _component

    override fun onCreate() {
        super.onCreate()

        Fabric.with(fabric)
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        Realm.init(this)

        val configSettings = FirebaseRemoteConfigSettings.Builder().apply {
            setDeveloperModeEnabled(BuildConfig.DEBUG)
        }.build()
        FirebaseRemoteConfig.getInstance().setConfigSettings(configSettings)
        FirebaseRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults)
    }
}