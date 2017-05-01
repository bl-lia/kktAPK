package com.bl_lia.kirakiratter.presentation.adapter.account

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
import com.bl_lia.kirakiratter.domain.entity.Status
import com.bl_lia.kirakiratter.domain.extension.asHtml
import com.bl_lia.kirakiratter.domain.value_object.Media
import com.bl_lia.kirakiratter.presentation.transform.AvatarTransformation
import com.squareup.picasso.Picasso
import io.reactivex.Observable

class AccountStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_account_status

        fun newInstance(parent: View): AccountStatusViewHolder = AccountStatusViewHolder(parent)
    }

    val onClickReply = Observable.create<Status> { subscriber ->
        replyButton.setOnClickListener {
            subscriber.onNext(status)
        }
    }

    val onClickReblog = Observable.create<Status> { subscriber ->
        reblogButton.setOnClickListener {
            subscriber.onNext(status)
        }
    }

    val onClickFavourite = Observable.create<Status> { subscriber ->
        favouriteButton.setOnClickListener {
            subscriber.onNext(status)
        }
    }

    val onClickTranslate = Observable.create<Status> { subscriber ->
        translateButton.setOnClickListener {
            subscriber.onNext(status)
        }
    }

    val onClickImage = Observable.create<Pair<Status, Int>> { subscriber ->
        itemImage1.setOnClickListener {
            subscriber.onNext(Pair(status, 0))
        }
        itemImage2.setOnClickListener {
            subscriber.onNext(Pair(status, 1))
        }
    }

    val onClickAccount = Observable.create<Pair<Account, ImageView>> { subscriber ->
        avatarImage.setOnClickListener {
            val target = status.reblog ?: status
            subscriber.onNext(Pair<Account, ImageView>(target.account!!, avatarImage))
        }
    }

    private val statusBody: TextView by lazy {
        itemView.findViewById(R.id.status_body) as TextView
    }

    private val avatarImage: ImageView by lazy {
        itemView.findViewById(R.id.image_avatar) as ImageView
    }

    private val reblogImage: ImageView by lazy {
        itemView.findViewById(R.id.image_reblog) as ImageView
    }

    private val accountName: TextView by lazy {
        itemView.findViewById(R.id.account_name) as TextView
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

    private val itemImage1: ImageView by lazy {
        itemView.findViewById(R.id.item_image_1) as ImageView
    }

    private val sensitive1: ImageView by lazy {
        itemView.findViewById(R.id.item_image_sensitive_1) as ImageView
    }

    private val sensitive2: ImageView by lazy {
        itemView.findViewById(R.id.item_image_sensitive_2) as ImageView
    }

    private val itemImage2: ImageView by lazy {
        itemView.findViewById(R.id.item_image_2) as ImageView
    }

    private val downButton: Button by lazy {
        itemView.findViewById(R.id.button_down) as Button
    }

    private val statusWarning: TextView by lazy {
        itemView.findViewById(R.id.status_warning) as TextView
    }

    private val contentWarningLayout: ViewGroup by lazy {
        itemView.findViewById(R.id.layout_content_warning) as ViewGroup
    }

    private val imagesLayout: ViewGroup by lazy {
        itemView.findViewById(R.id.layout_images) as ViewGroup
    }

    private val layoutBody: ViewGroup by lazy {
        itemView.findViewById(R.id.layout_status_body) as ViewGroup
    }

    private val translateButton: Button by lazy {
        itemView.findViewById(R.id.button_translate) as Button
    }

    private lateinit var status: Status


    fun bind(status: Status) {
        this.status = status
        val target: Status = status.reblog ?: status

        statusBody.text =
                if (target.content?.translatedText.isNullOrEmpty()) {
                    target.content?.body?.asHtml()?.trim()
                } else {
                    target.content?.translatedText
                }
        statusBody.movementMethod = LinkMovementMethod.getInstance()
        statusWarning.text = target.content?.header

        reblogImage.visibility = if (status.reblog != null) View.VISIBLE else View.INVISIBLE
        accountName.text =
                if (target.account?.displayName.isNullOrEmpty()) {
                    target.account?.userName
                } else {
                    target.account?.displayName
                }

        reblogButton.background =
                if (target.reblogged) {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_reblog)
                } else {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_unreblog)
                }

        favouriteButton.background =
                if (target.favourited) {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_favourite)
                } else {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_unfavourite)
                }

        downButton.setOnClickListener {
            layoutBody.visibility = if (layoutBody.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        if (target.content?.header.isNullOrEmpty()) {
            contentWarningLayout.visibility = View.GONE
            layoutBody.visibility = View.VISIBLE
        } else {
            contentWarningLayout.visibility = View.VISIBLE
            layoutBody.visibility = View.GONE
        }

        resetMedia(target.mediaAttachments)
        target.mediaAttachments
                .forEachIndexed { index, media ->
                    when (index) {
                        0 -> {
                            itemImage1.visibility = View.VISIBLE
                            if (!target.sensitive) {
                                Picasso.with(itemView.context).load(media.previewUrl).into(itemImage1)
                            } else {
                                sensitive1.visibility = View.VISIBLE
                            }
                        }
                        1 -> {
                            itemImage2.visibility = View.VISIBLE
                            if (!target.sensitive) {
                                Picasso.with(itemView.context).load(media.previewUrl).into(itemImage2)
                            } else {
                                sensitive2.visibility = View.VISIBLE
                            }
                        }
                    }
                }

        target.account?.let {
            val color = ContextCompat.getColor(itemView.context, R.color.content_border)
            Picasso.with(itemView.context).load(it.avatar).transform(AvatarTransformation(color)).into(avatarImage)
        }

        sensitive1.setOnClickListener {
            sensitive1.visibility = View.GONE
            target.mediaAttachments[0].let { media ->
                Picasso.with(itemView.context).load(media.previewUrl).into(itemImage1)
            }
        }
        sensitive2.setOnClickListener {
            sensitive2.visibility = View.GONE
            target.mediaAttachments[1].let { media ->
                Picasso.with(itemView.context).load(media.previewUrl).into(itemImage2)
            }
        }
    }

    private fun resetMedia(mediaList: List<Media>) {
        itemImage1.setImageResource(0)
        itemImage1.visibility = View.GONE
        sensitive1.visibility = View.GONE
        itemImage2.setImageResource(0)
        itemImage2.visibility = View.GONE
        sensitive2.visibility = View.GONE

        imagesLayout.visibility = if (mediaList.isNotEmpty()) View.VISIBLE else View.GONE
    }
}