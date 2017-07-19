package com.bl_lia.kirakiratter.presentation.adapter.timeline

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bl_lia.kirakiratter.presentation.fragment.NotificationFragment
import com.bl_lia.kirakiratter.presentation.fragment.TimelineFragment

class TimelineFragmentPagerAdapter(
        private val fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = 3

    private val homeTimelineFragment by lazy { TimelineFragment.newInstance(TimelineFragment.Scope.Home) }
    private val localTimelineFragment by lazy { TimelineFragment.newInstance(TimelineFragment.Scope.Local) }
    private val notificationFragment by lazy { NotificationFragment.newInstance() }

    override fun getItem(position: Int): Fragment? =
            when (position) {
                0 -> homeTimelineFragment
                1 -> localTimelineFragment
                2 -> notificationFragment
                else -> null
            }

    fun switchSimpleMode(simpleModeEnabled: Boolean) {
        homeTimelineFragment.switchSimpleMode(simpleModeEnabled)
        localTimelineFragment.switchSimpleMode(simpleModeEnabled)
    }

}