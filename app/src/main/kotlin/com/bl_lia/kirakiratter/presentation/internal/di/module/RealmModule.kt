package com.bl_lia.kirakiratter.presentation.internal.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class RealmModule {

    @Provides
    @Singleton
    fun provideRealmConfiguration(application: Application) =
            RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build()
}