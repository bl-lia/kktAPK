package com.bl_lia.kirakiratter.presentation.adapter.navigation_drawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject

class NavigationDrawerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Menu(val id: Int) {
        License(0), Thanks(1), PushNotification(2), SimpleMode(3)
    }

    val onClickMenu = PublishSubject.create<Menu>()
    val onChangePushNotificationSetting = PublishSubject.create<Boolean>()
    val onChangeSimpleModeSetting = PublishSubject.create<Boolean>()

    var pushFeatureEnabled: Boolean = false

    private var pushEnabled: Boolean = false
    private var simpleMode: Boolean = false

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
            3 -> {
                val view = LayoutInflater.from(parent?.context).inflate(NavigationDrawerSimpleModeViewHolder.LAYOUT, parent, false)
                return NavigationDrawerSimpleModeViewHolder.newInstance(view)
            }
            else -> throw RuntimeException("no viewtype")
        }
    }

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0, 1 -> 1
                2    -> {
                    if (pushFeatureEnabled && Menu.PushNotification.id == position) {
                        2
                    } else {
                        3
                    }
                }
                3    -> 3
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
            holder.bind("Push Notification", pushEnabled)
            holder.onCheckedChange.subscribe(onChangePushNotificationSetting)
        }

        if (holder is NavigationDrawerSimpleModeViewHolder) {
            holder.bind("Simple Mode", simpleMode)
            holder.onCheckedChange.subscribe(onChangeSimpleModeSetting)
        }
    }

    fun pushConfigChanged(enabled: Boolean) {
        pushEnabled = enabled
        notifyItemChanged(Menu.PushNotification.id)
    }

    fun simpleModeChanged(enabled: Boolean) {
        simpleMode = enabled
        notifyItemChanged(Menu.SimpleMode.id)
    }
}