package com.bl_lia.kirakiratter.presentation.adapter.account

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Status
import io.reactivex.subjects.PublishSubject

class AccountAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val onClickReply = PublishSubject.create<Status>()
    val onClickReblog = PublishSubject.create<Status>()
    val onClickFavourite = PublishSubject.create<Status>()
    val onClickTranslate = PublishSubject.create<Status>()
    val onClickMedia = PublishSubject.create<Pair<Status, Int>>()
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
        val view = LayoutInflater.from(parent?.context).inflate(AccountStatusViewHolder.LAYOUT, parent, false)
        return AccountStatusViewHolder.newInstance(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is AccountStatusViewHolder) {
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
}