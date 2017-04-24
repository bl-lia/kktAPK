package com.bl_lia.kirakiratter.presentation.internal.di.component

import dagger.Component
import com.bl_lia.kirakiratter.presentation.fragment.MainFragment
import com.bl_lia.kirakiratter.presentation.fragment.TimelineFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.TimelineModule

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, TimelineModule::class))
interface TimelineComponent {
    fun inject(fragment: TimelineFragment)
}