package com.bl_lia.kirakiratter

import android.app.Application
import com.bl_lia.kirakiratter.presentation.internal.di.HasComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.ApplicationComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerApplicationComponent
import com.bl_lia.kirakiratter.presentation.internal.di.module.ApplicationModule
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import io.fabric.sdk.android.Fabric
import io.realm.Realm

class App : Application(), HasComponent<ApplicationComponent> {

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
        Stetho.initializeWithDefaults(this)
        Realm.init(this)

        FirebaseRemoteConfig.getInstance().setDefaults(R.xml.remote_config_defaults)
    }
}