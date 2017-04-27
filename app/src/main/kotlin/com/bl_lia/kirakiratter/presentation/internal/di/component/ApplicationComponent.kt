package com.bl_lia.kirakiratter.presentation.internal.di.component

import android.app.Application
import com.bl_lia.kirakiratter.domain.executor.PostExecutionThread
import com.bl_lia.kirakiratter.domain.executor.ThreadExecutor
import com.bl_lia.kirakiratter.domain.repository.*
import com.bl_lia.kirakiratter.presentation.internal.di.module.ApplicationModule
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun application(): Application
    fun authRepository(): AuthRepository
    fun timelineRepository(): TimelineRepository
    fun statusRepository(): StatusRepository
    fun translationRepository(): TranslationRepository
    fun notificationRepository(): NotificationRepository
    fun accountRepository(): AccountRepository
    fun pushNotificationRepository(): PushNotificationRepository
    fun threadExecutor(): ThreadExecutor
    fun postExecutionThread(): PostExecutionThread

    @Named("appName")
    fun appName(): String

    @Named("oauthRedirectUri")
    fun oauthRedirectUri(): String

    @Named("oauthScopes")
    fun oauthScopes(): String

    @Named("appWebsite")
    fun appWebsite(): String
}