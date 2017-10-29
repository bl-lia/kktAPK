package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bl_lia.kirakiratter.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item_navigation_drawer.view.*

class NavigationDrawerViewHolder(val parent: View) : RecyclerView.ViewHolder(parent) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_navigation_drawer

        fun newInstance(parent: View): NavigationDrawerViewHolder = NavigationDrawerViewHolder(parent)
    }

    val onClickItem = Observable.create<NavigationDrawerAdapter.Menu> { subscriber ->
        itemView.setOnClickListener {
            subscriber.onNext(menu)
        }
    }

    private lateinit var menu: NavigationDrawerAdapter.Menu

    fun bind(menu: NavigationDrawerAdapter.Menu) {
        this.menu = menu
        itemView.menu_text.text = when (menu) {
            NavigationDrawerAdapter.Menu.License -> "Open Source License"
            NavigationDrawerAdapter.Menu.Thanks  -> "Special Thanks"
            else -> null
        }
    }
}