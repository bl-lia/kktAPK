package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.fragment.TimelineFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.StatusModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.TimelineModule
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, TimelineModule::class, StatusModule::class))
interface TimelineComponent {
    fun inject(fragment: TimelineFragment)
}