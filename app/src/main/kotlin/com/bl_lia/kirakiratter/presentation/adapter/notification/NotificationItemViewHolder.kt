package com.bl_lia.kirakiratter.presentation.adapter.notification

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.domain.entity.Account
import com.bl_lia.kirakiratter.domain.entity.Notification
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.asHtml
import com.bl_lia.kirakiratter.presentation.transform.AvatarTransformation
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.list_item_notification.view.*

class NotificationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_notification

        fun newInstance(parent: View): NotificationItemViewHolder = NotificationItemViewHolder(parent)
    }

    val onClickAccount = Observable.create<Pair<Account, ImageView>> { subscriber ->
        itemView.image_avatar.setOnClickListener {
            subscriber.onNext(Pair<Account, ImageView>(notification.status?.account!!, itemView.image_avatar))
        }
    }

    val onClickReply = Observable.create<Status> { subscriber ->
        itemView.button_reply.setOnClickListener {
            subscriber.onNext(notification.status!!)
        }
    }

    val onClickReblog = Observable.create<Notification> { subscriber ->
        itemView.button_reblog.setOnClickListener {
            subscriber.onNext(notification)
        }
    }

    val onClickFavourite = Observable.create<Notification> { subscriber ->
        itemView.button_favourite.setOnClickListener {
            subscriber.onNext(notification)
        }
    }

    val onClickTranslate = Observable.create<Notification> { subscriber ->
        itemView.button_translate.setOnClickListener {
            subscriber.onNext(notification)
        }
    }

    private lateinit var notification: Notification

    fun bind(notification: Notification) {
        this.notification = notification

        when (notification.type) {
            "reblog" -> {
                itemView.image_type.setBackgroundResource(R.drawable.ic_reblog_reblog)
                itemView.layout_action.visibility = View.GONE
            }
            "favourite" -> {
                itemView.image_type.setBackgroundResource(R.drawable.ic_star_favourite)
                itemView.layout_action.visibility = View.GONE
            }
            "follow" -> {
                itemView.image_type.setBackgroundResource(R.drawable.ic_reblog_reblog)
                itemView.layout_action.visibility = View.GONE
            }
            "mention" -> {
                itemView.image_type.setBackgroundResource(R.drawable.ic_reply_unreply)
                itemView.layout_action.visibility = View.VISIBLE

                val target = notification.status?.reblog ?: notification.status

                itemView.button_reblog.background =
                        if (target?.reblogged ?: false) {
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_reblog)
                        } else {
                            ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_unreblog)
                        }

                itemView.button_favourite.background =
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
                itemView.text_body.text = if (notification.status?.content?.header.isNullOrEmpty()) {
                    notification.status?.content?.body?.asHtml()?.trim()
                } else {
                    notification.status?.content?.header?.asHtml()?.trim().toString() + "\n" + notification.status?.content?.body?.asHtml()?.trim().toString()
                }
                notification.status?.account?.loadAvater(itemView.context, itemView.image_avatar)
            }
            "mention" -> {
                val target = notification.status?.reblog ?: notification.status
                itemView.text_body.text = if (target?.content?.translatedText.isNullOrEmpty()) {
                    target?.content?.body?.asHtml()?.trim()
                } else {
                    target?.content?.translatedText
                }
                notification.account?.loadAvater(itemView.context, itemView.image_avatar)
            }
        }

        itemView.text_body.movementMethod = LinkMovementMethod.getInstance()


        itemView.text_notify.text = notification.notifiedMessage(itemView.context)
        itemView.text_notify.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun Account.loadAvater(context: Context, imageView: ImageView) {
        Picasso.with(context)
                .load(avatar)
                .transform(AvatarTransformation(context, R.color.content_border))
                .into(imageView)
    }
}
