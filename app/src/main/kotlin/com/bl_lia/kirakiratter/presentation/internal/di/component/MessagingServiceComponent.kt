package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.MessagingServiceModule
import com.bl_lia.kirakiratter.presentation.service.FetchNotificationJobService
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(MessagingServiceModule::class))
interface MessagingServiceComponent {
    fun inject(service: FetchNotificationJobService)
}