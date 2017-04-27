package com.bl_lia.kirakiratter.presentation.adapter.notification

import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.presentation.transform.AvatarTransformation
import com.squareup.picasso.Picasso
import io.reactivex.Observable

class NotificationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_notification

        fun newInstance(parent: View): NotificationItemViewHolder = NotificationItemViewHolder(parent)
    }

    val onClickAccount = Observable.create<Pair<Account, ImageView>> { subscriber ->
        avatarImage.setOnClickListener {
            subscriber.onNext(Pair<Account, ImageView>(notification.status?.account!!, avatarImage))
        }
    }

    private val notifyText: TextView by lazy {
        itemView.findViewById(R.id.text_notify) as TextView
    }

    private val avatarImage: ImageView by lazy {
        itemView.findViewById(R.id.image_avatar) as ImageView
    }

    private val bodyText: TextView by lazy {
        itemView.findViewById(R.id.text_body) as TextView
    }

    private val notificationType: ImageView by lazy {
        itemView.findViewById(R.id.image_type) as ImageView
    }

    private lateinit var notification: Notification

    fun bind(notification: Notification) {
        this.notification = notification

        when (notification.type) {
            "reblog" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reblog_reblog)
            }
            "favourite" -> {
                notificationType.setBackgroundResource(R.drawable.ic_star_favourite)
            }
            "follow" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reblog_reblog)
            }
            "mention" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reply_unreply)
            }
        }

        notifyText.text = notification.notifiedMessage(itemView.context)
        notifyText.movementMethod = LinkMovementMethod.getInstance()

        notification.status?.let { status ->
            bodyText.text = status.content.body?.trim()
            bodyText.movementMethod = LinkMovementMethod.getInstance()

            status.account?.let { account ->
                Picasso.with(itemView.context)
                        .load(account.avatar)
                        .transform(AvatarTransformation(ContextCompat.getColor(itemView.context, R.color.content_border)))
                        .into(avatarImage)
            }
        }

    }
}