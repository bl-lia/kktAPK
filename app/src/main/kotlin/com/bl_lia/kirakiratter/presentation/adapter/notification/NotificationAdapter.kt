package com.bl_lia.kirakiratter.presentation.adapter.notification

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.subjects.PublishSubject

class NotificationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType(val value: Int) {
        Follow(1), Boost(2), Favourite(3), Reblog(4), Mention(5);

        companion object {
            fun valueOf(value: Int): ViewType? =
                    ViewType.values().find { it.value == value }
        }
    }

    val onClickAccount   = PublishSubject.create<Pair<Account, ImageView>>()
    val onClickReply     = PublishSubject.create<Status>()
    val onClickReblog    = PublishSubject.create<Notification>()
    val onClickFavourite = PublishSubject.create<Notification>()

    private val list: MutableList<Notification> = mutableListOf()

    val maxId: Int?
        get() {
            if (list.isEmpty()) {
                return null
            }

            return list.last().id
        }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =
            when (ViewType.valueOf(viewType)) {
                ViewType.Boost,
                        ViewType.Favourite,
                        ViewType.Reblog,
                        ViewType.Mention -> {
                    val view = LayoutInflater.from(parent?.context).inflate(NotificationItemViewHolder.LAYOUT, parent, false)
                    NotificationItemViewHolder.newInstance(view)
                }
                ViewType.Follow -> {
                    val view = LayoutInflater.from(parent?.context).inflate(NotificationFollowItemViewHolder.LAYOUT, parent, false)
                    NotificationFollowItemViewHolder.newInstance(view)
                }
                else -> throw RuntimeException("No ViewHolder found")
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is NotificationItemViewHolder) {
            holder.bind(list[position])
            holder.onClickAccount.subscribe(onClickAccount)
            holder.onClickReply.subscribe(onClickReply)
            holder.onClickReblog.subscribe(onClickReblog)
            holder.onClickFavourite.subscribe(onClickFavourite)
            return
        }

        if (holder is NotificationFollowItemViewHolder) {
            holder.bind(list[position])
            holder.onClickAccount.subscribe(onClickAccount)
            return
        }
    }

    override fun getItemViewType(position: Int): Int =
            when (list[position].type) {
                "follow" -> ViewType.Follow.value
                "favourite" -> ViewType.Favourite.value
                "reblog" -> ViewType.Reblog.value
                "boost" -> ViewType.Boost.value
                "mention" -> ViewType.Mention.value
                else -> -1
            }

    fun reset(newList: List<Notification>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun add(moreList: List<Notification>) {
        list.addAll(moreList)
        notifyDataSetChanged()
    }

    fun update(notification: Notification) {
        list.indexOfFirst {
            it.id == notification.id
        }.also { index ->
            if (index > -1) {
                list.set(index, notification)
                notifyItemChanged(index)
            }
        }
    }
}