package com.bl_lia.kirakiratter.presentation.internal.di.component

import dagger.Component
import com.bl_lia.kirakiratter.presentation.fragment.MainFragment
import com.bl_lia.kirakiratter.presentation.fragment.NavigationDrawerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.AuthModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, AuthModule::class))
interface AuthComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: NavigationDrawerFragment)
}