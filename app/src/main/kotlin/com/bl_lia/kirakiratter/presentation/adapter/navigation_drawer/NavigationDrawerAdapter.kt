package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject

class NavigationDrawerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Menu(val id: Int) {
        License(1), Thanks(2), PushNotification(3)
    }

    val onClickMenu = PublishSubject.create<Menu>()
    val onChangePushNotificationSetting = PublishSubject.create<Boolean>()

    var pushFeatureEnabled: Boolean = false

    private var pushEnabled: Boolean = false

    override fun getItemCount(): Int = if (pushFeatureEnabled) Menu.values().size else Menu.values().size - 1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val view  = LayoutInflater.from(parent?.context).inflate(NavigationDrawerViewHolder.LAYOUT, parent, false)
                return NavigationDrawerViewHolder.newInstance(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent?.context).inflate(NavigationDrawerPushNotificationViewHolder.LAYOUT, parent, false)
                return NavigationDrawerPushNotificationViewHolder.newInstance(view)
            }
            else -> throw RuntimeException("no viewtype")
        }
    }

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0, 1 -> 1
                2    -> 2
                else -> -1
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is NavigationDrawerViewHolder) {
            when (position) {
                0 -> {
                    holder.bind(Menu.License)
                    holder.onClickItem.subscribe(onClickMenu)
                    return
                }
                1 -> {
                    holder.bind(Menu.Thanks)
                    holder.onClickItem.subscribe(onClickMenu)
                    return
                }
            }
        }

        if (holder is NavigationDrawerPushNotificationViewHolder) {
            holder.bind(pushEnabled)
            holder.onCheckedChange.subscribe(onChangePushNotificationSetting)
        }
    }

    fun pushEnabled(enabled: Boolean) {
        pushEnabled = enabled
        notifyItemChanged(2)
    }
}