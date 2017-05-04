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

    private val items = arrayOf<Fragment>(
            TimelineFragment.newInstance(TimelineFragment.Scope.Home),
            TimelineFragment.newInstance(TimelineFragment.Scope.Local),
            NotificationFragment.newInstance())

    override fun getItem(position: Int): Fragment? = items[position]

}