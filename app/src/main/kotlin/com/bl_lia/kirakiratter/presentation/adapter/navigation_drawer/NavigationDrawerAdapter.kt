package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject

class NavigationDrawerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Menu(val id: Int) {
        License(1), Thanks(2)
    }

    val onClickMenu = PublishSubject.create<Menu>()

    override fun getItemCount(): Int = Menu.values().size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view  = LayoutInflater.from(parent?.context).inflate(NavigationDrawerViewHolder.LAYOUT, parent, false)
        return NavigationDrawerViewHolder.newInstance(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is NavigationDrawerViewHolder) {
            when (position) {
                0 -> {
                    holder.bind(Menu.License)
                    holder.onClickItem.subscribe(onClickMenu)
                }
                1 -> {
                    holder.bind(Menu.Thanks)
                    holder.onClickItem.subscribe(onClickMenu)
                }
            }
        }
    }
}