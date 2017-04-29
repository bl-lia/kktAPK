package com.bl_lia.kirakiratter.presentation.adapter.notification

import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.entity.Status
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

    val onClickReply = Observable.create<Status> { subscriber ->
        replyButton.setOnClickListener {
            subscriber.onNext(notification.status)
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

    private val replyButton: Button by lazy {
        itemView.findViewById(R.id.button_reply) as Button
    }

    private val reblogButton: Button by lazy {
        itemView.findViewById(R.id.button_reblog) as Button
    }

    private val favouriteButton: Button by lazy {
        itemView.findViewById(R.id.button_favourite) as Button
    }

    private val translateButton: Button by lazy {
        itemView.findViewById(R.id.button_translate) as Button
    }

    private lateinit var notification: Notification

    fun bind(notification: Notification) {
        this.notification = notification

        when (notification.type) {
            "reblog" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reblog_reblog)
                replyButton.visibility = View.INVISIBLE
                reblogButton.visibility = View.INVISIBLE
                favouriteButton.visibility = View.INVISIBLE
                translateButton.visibility = View.INVISIBLE
            }
            "favourite" -> {
                notificationType.setBackgroundResource(R.drawable.ic_star_favourite)
                replyButton.visibility = View.INVISIBLE
                reblogButton.visibility = View.INVISIBLE
                favouriteButton.visibility = View.INVISIBLE
                translateButton.visibility = View.INVISIBLE
            }
            "follow" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reblog_reblog)
                replyButton.visibility = View.INVISIBLE
                reblogButton.visibility = View.INVISIBLE
                favouriteButton.visibility = View.INVISIBLE
                translateButton.visibility = View.INVISIBLE
            }
            "mention" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reply_unreply)
                replyButton.visibility = View.VISIBLE
                reblogButton.visibility = View.INVISIBLE
                favouriteButton.visibility = View.INVISIBLE
                translateButton.visibility = View.INVISIBLE
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