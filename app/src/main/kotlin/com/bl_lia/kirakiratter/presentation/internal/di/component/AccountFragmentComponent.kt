package com.bl_lia.kirakiratter.presentation.internal.di.component

import com.bl_lia.kirakiratter.presentation.fragment.AccountFragment
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment
import com.bl_lia.kirakiratter.presentation.internal.di.module.AccountFragmentModule
import com.bl_lia.kirakiratter.presentation.internal.di.module.FragmentModule
import dagger.Component

@PerFragment
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(FragmentModule::class, AccountFragmentModule::class))
interface AccountFragmentComponent {
    fun inject(fragment: AccountFragment)
}