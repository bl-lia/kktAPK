package com.bl_lia.kirakiratter.presentation.adapter.account

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.presentation.adapter.timeline.TimelineItemViewHolder
import io.reactivex.subjects.PublishSubject

class AccountAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val onClickReply = PublishSubject.create<Status>()
    val onClickReblog = PublishSubject.create<Status>()
    val onClickFavourite = PublishSubject.create<Status>()
    val onClickTranslate = PublishSubject.create<Status>()
    val onClickMedia = PublishSubject.create<Triple<Status, Int, ImageView>>()
    val onClickAccount = PublishSubject.create<Pair<Account, ImageView>>()

    private val list: MutableList<Status> = mutableListOf()

    val maxId: Int?
        get() {
            if (list.isEmpty()) {
                return null
            }

            return list.last().id
        }

    val sinceId: String?
        get() {
            if (list.isEmpty()) {
                return null
            }

            return list.first().id.toString()
        }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(TimelineItemViewHolder.LAYOUT, parent, false)
        return TimelineItemViewHolder.newInstance(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is TimelineItemViewHolder) {
            holder.bind(list[position])
            holder.onClickReply.subscribe(onClickReply)
            holder.onClickReblog.subscribe(onClickReblog)
            holder.onClickFavourite.subscribe(onClickFavourite)
            holder.onClickTranslate.subscribe(onClickTranslate)
            holder.onClickImage.subscribe(onClickMedia)
            holder.onClickAccount.subscribe(onClickAccount)
        }
    }

    fun reset(newList: List<Status>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun add(newList: List<Status>) {
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun update(status: Status) {
        list.indexOfFirst {
            val target = it.reblog ?: it
            target.id == status.id
        }
                .also { index ->
                    if (index > -1) {
                        list.set(index, status)
                        notifyItemChanged(index)
                    }
                }
    }

    fun addTranslatedText(status: Status, translatedText: String) {
        list.indexOfFirst {
            it.id == status.id
        }.also { index ->
            if (index > -1) {
                val s = if (status.reblog != null) {
                    status.copy(
                            reblog = status.reblog.copy(
                                    content = status.reblog.content?.copy(translatedText = translatedText)
                            )
                    )
                } else {
                    status.copy(
                            content = status.content?.copy(translatedText = translatedText)
                    )
                }
                list.set(index, s)
                notifyItemChanged(index)
            }
        }
    }
}