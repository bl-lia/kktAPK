package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.annotation.LayoutRes
import android.view.View

class NavigationDrawerSimpleModeViewHolder(itemView: View) : NavigationDrawerSwitchConfigViewHolder(itemView) {

    companion object {
        @LayoutRes
        const val LAYOUT = NavigationDrawerSwitchConfigViewHolder.LAYOUT

        fun newInstance(parent: View): NavigationDrawerSimpleModeViewHolder = NavigationDrawerSimpleModeViewHolder(parent)
    }
}