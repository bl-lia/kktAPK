package com.bl_lia.kirakiratter.presentation.internal.di.component

import dagger.Component
import com.bl_lia.kirakiratter.presentation.fragment.KatsuFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.StatusModule

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, StatusModule::class))
interface StatusComponent {
    fun inject(fragment: KatsuFragment)
}