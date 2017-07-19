package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
import android.view.View
import android.widget.TextView
import com.bl_lia.kirakiratter.R
import io.reactivex.Observable

class NavigationDrawerSwitchConfigViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

    companion object {
        @LayoutRes
        const val LAYOUT = R.layout.list_item_navigation_drawer_push_notification

        fun newInstance(parent: View): NavigationDrawerSwitchConfigViewHolder = NavigationDrawerSwitchConfigViewHolder(parent)
    }

    val onCheckedChange = Observable.create<Boolean> { subscriber ->
        switch.setOnCheckedChangeListener { button, checked ->
            subscriber.onNext(checked)
        }
    }

    private val menuText: TextView by lazy {
        itemView.findViewById(R.id.menu_text) as TextView
    }

    private val switch: SwitchCompat by lazy {
        itemView.findViewById(R.id.menu_switch) as SwitchCompat
    }

    fun bind(text: String, enabled: Boolean) {
        menuText.text = text
        switch.isChecked = enabled
    }
}