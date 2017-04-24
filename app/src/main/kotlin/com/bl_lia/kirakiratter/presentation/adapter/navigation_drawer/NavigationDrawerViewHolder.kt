package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bl_lia.kirakiratter.R
import io.reactivex.Observable

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

    private val menuText:TextView by lazy {
        itemView.findViewById(R.id.menu_text) as TextView
    }

    private lateinit var menu: NavigationDrawerAdapter.Menu

    fun bind(menu: NavigationDrawerAdapter.Menu) {
        this.menu = menu

        when (menu) {
            NavigationDrawerAdapter.Menu.License -> {
                menuText.text = "Open Source License"
            }
            NavigationDrawerAdapter.Menu.Thanks -> {
                menuText.text = "Special Thanks"
            }
        }
    }
}