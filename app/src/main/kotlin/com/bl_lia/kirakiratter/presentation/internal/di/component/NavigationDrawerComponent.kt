package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.fragment.NavigationDrawerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.AuthModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.PushNotificationModule
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, PushNotificationModule::class, AuthModule::class))
interface NavigationDrawerComponent {

    fun inject(fragment: NavigationDrawerFragment)
}