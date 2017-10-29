package com.bl_lia.kirakiratter.presentation.adapter.notification

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.presentation.transform.AvatarTransformation
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item_notification_follow.view.*

class NotificationFollowItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_notification_follow

        fun newInstance(parent: View): NotificationFollowItemViewHolder = NotificationFollowItemViewHolder(parent)
    }

    val onClickAccount = Observable.create<Pair<Account, ImageView>> { subscriber ->
        itemView.image_avatar.setOnClickListener {
            subscriber.onNext(Pair<Account, ImageView>(notification.account!!, itemView.image_avatar))
        }
    }

    private lateinit var notification: Notification

    fun bind(notification: Notification) {
        this.notification = notification

        notification.account?.let { account ->
            Picasso.with(itemView.context)
                    .load(account.avatar)
                    .transform(AvatarTransformation(itemView.context, R.color.content_border))
                    .into(itemView.image_avatar)
            itemView.text_head_message.text = itemView?.resources?.getString(R.string.notification_follow)?.format(account.preparedDisplayName)
        }
    }
}