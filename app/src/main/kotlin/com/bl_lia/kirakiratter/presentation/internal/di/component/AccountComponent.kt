package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.activity.AccountActivity
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import com.bl_lia.kirakiratter.presentation.internal.di.module.AccountModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.ActivityModule
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class, AccountModule::class))
interface AccountComponent {
    fun inject(activity: AccountActivity)
}