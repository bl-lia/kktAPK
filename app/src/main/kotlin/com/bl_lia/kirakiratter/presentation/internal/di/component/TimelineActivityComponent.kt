package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.activity.TimelineActivity
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import com.bl_lia.kirakiratter.presentation.internal.di.module.TimelineActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(TimelineActivityModule::class))
interface TimelineActivityComponent {
    fun inject(activity: TimelineActivity)
}