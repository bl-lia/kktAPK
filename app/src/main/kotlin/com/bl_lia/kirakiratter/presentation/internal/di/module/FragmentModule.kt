package com.bl_lia.kirakiratter.presentation.internal.di.module

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides
import com.bl_lia.kirakiratter.presentation.internal.di.PerFragment

@Module
class FragmentModule(
        private val fragment: Fragment
) {
    @Provides
    @PerFragment
    internal fun provideFragment(): Fragment = fragment
}