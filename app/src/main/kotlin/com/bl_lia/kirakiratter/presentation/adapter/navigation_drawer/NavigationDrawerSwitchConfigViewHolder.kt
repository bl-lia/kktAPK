package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bl_lia.kirakiratter.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item_navigation_drawer_push_notification.view.*

open class NavigationDrawerSwitchConfigViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

    companion object {
        @LayoutRes
        const val LAYOUT = R.layout.list_item_navigation_drawer_push_notification
    }

    val onCheckedChange = Observable.create<Boolean> { subscriber ->
        itemView.menu_switch.setOnCheckedChangeListener { button, checked ->
            subscriber.onNext(checked)
        }
    }

    fun bind(text: String, enabled: Boolean) {
        itemView.menu_text.text = text
        itemView.menu_switch.isChecked = enabled
    }
}