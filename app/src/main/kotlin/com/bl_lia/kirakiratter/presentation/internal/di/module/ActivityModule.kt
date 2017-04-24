package com.bl_lia.kirakiratter.presentation.internal.di.module

import android.support.v7.app.AppCompatActivity
import com.bl_lia.kirakiratter.presentation.internal.di.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
        private val activity: AppCompatActivity
) {
    @Provides
    @PerActivity
    internal fun provideActivity(): AppCompatActivity = activity
}