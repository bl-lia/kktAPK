package com.bl_lia.kirakiratter.presentation.adapter.notification

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.asHtml
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

    val onClickReblog = Observable.create<Notification> { subscriber ->
        reblogButton.setOnClickListener {
            subscriber.onNext(notification)
        }
    }

    val onClickFavourite = Observable.create<Notification> { subscriber ->
        favouriteButton.setOnClickListener {
            subscriber.onNext(notification)
        }
    }

    val onClickTranslate = Observable.create<Notification> { subscriber ->
        translateButton.setOnClickListener {
            subscriber.onNext(notification)
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

    private val actionLayout: ViewGroup by lazy {
        itemView.findViewById(R.id.layout_action) as ViewGroup
    }

    private lateinit var notification: Notification

    fun bind(notification: Notification) {
        this.notification = notification

        when (notification.type) {
            "reblog" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reblog_reblog)
                actionLayout.visibility = View.GONE
            }
            "favourite" -> {
                notificationType.setBackgroundResource(R.drawable.ic_star_favourite)
                actionLayout.visibility = View.GONE
            }
            "follow" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reblog_reblog)
                actionLayout.visibility = View.GONE
            }
            "mention" -> {
                notificationType.setBackgroundResource(R.drawable.ic_reply_unreply)
                actionLayout.visibility = View.VISIBLE

                val target = notification.status?.reblog ?: notification.status

                reblogButton.background =
                        if (target?.reblogged ?: false) {
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_reblog)
                        } else {
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_unreblog)
                        }

                favouriteButton.background =
                        if (target?.favourited ?: false) {
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_favourite)
                        } else {
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_unfavourite)
                        }

            }
        }

        when (notification.type) {
            "reblog",
            "favourite",
            "follow" -> {
                bodyText.text = if (notification.status?.content?.header.isNullOrEmpty()) {
                    notification.status?.content?.body?.asHtml()?.trim()
                } else {
                    notification.status?.content?.header?.asHtml()?.trim().toString() + "\n" + notification.status?.content?.body?.asHtml()?.trim().toString()
                }
                notification.status?.account?.loadAvater(itemView.context, avatarImage)
            }
            "mention" -> {
                val target = notification.status?.reblog ?: notification.status
                bodyText.text = if (target?.content?.translatedText.isNullOrEmpty()) {
                    target?.content?.body?.asHtml()?.trim()
                } else {
                    target?.content?.translatedText
                }
                notification.account?.loadAvater(itemView.context, avatarImage)
            }
        }

        bodyText.movementMethod = LinkMovementMethod.getInstance()


        notifyText.text = notification.notifiedMessage(itemView.context)
        notifyText.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun Account.loadAvater(context: Context, imageView: ImageView) {
        Picasso.with(context)
                .load(avatar)
                .transform(AvatarTransformation(ContextCompat.getColor(context, R.color.content_border)))
                .into(imageView)
    }
}
