package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.fragment.MainFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.AuthModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, AuthModule::class))
interface AuthComponent {
    fun inject(fragment: MainFragment)
}