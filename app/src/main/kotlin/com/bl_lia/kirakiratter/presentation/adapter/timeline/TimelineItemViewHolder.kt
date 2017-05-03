package com.bl_lia.kirakiratter.presentation.adapter.timeline

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
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import io.reactivex.Observable

class TimelineItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.list_item_timeline

        fun newInstance(parent: View): TimelineItemViewHolder = TimelineItemViewHolder(parent)
    }

    val onClickAccount = Observable.create<Pair<Account, ImageView>> { subscriber ->
        avatarImage.setOnClickListener {
            val target = status.reblog ?: status
            subscriber.onNext(Pair<Account, ImageView>(target.account!!, avatarImage))
        }
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
        image1.setOnClickListener { subscriber.onNext(Pair(status, 0)) }
        image2.setOnClickListener { subscriber.onNext(Pair(status, 1)) }
        image3.setOnClickListener { subscriber.onNext(Pair(status, 2)) }
        image4.setOnClickListener { subscriber.onNext(Pair(status, 3)) }
    }

    // Boosted
    private val boostedLayout: ViewGroup by lazy { itemView.f(R.id.timeline_boosted) as ViewGroup }
    private val boostedMessage: TextView by lazy { itemView.f(R.id.text_boosted) as TextView }

    // Account
    private val avatarImage: ImageView by lazy { itemView.f(R.id.image_avatar) as ImageView }
    private val accountName: TextView by lazy { itemView.f(R.id.text_account_name) as TextView }

    // Content
    private val contentHeader: TextView by lazy { itemView.f(R.id.text_content_header) as TextView }
    private val contentBody: TextView by lazy { itemView.f(R.id.text_content_body) as TextView }
    private val showWarningContentImage: ImageView by lazy { itemView.f(R.id.image_content_show) as ImageView }

    // Media
    private val imagesLayout: FlexboxLayout by lazy { itemView.f(R.id.images) as FlexboxLayout }
    private val images: List<ImageView> by lazy { listOf(image1, image2, image3, image4) }
    private val image1: ImageView by lazy { itemView.f(R.id.image_1) as ImageView }
    private val image2: ImageView by lazy { itemView.f(R.id.image_2) as ImageView }
    private val image3: ImageView by lazy { itemView.f(R.id.image_3) as ImageView }
    private val image4: ImageView by lazy { itemView.f(R.id.image_4) as ImageView }
    private val sensitiveLayout: ViewGroup by lazy { itemView.f(R.id.layout_sensitive) as ViewGroup }

    // Action buttons
    private val replyButton: Button by lazy { itemView.f(R.id.button_reply) as Button }
    private val reblogButton: Button by lazy { itemView.f(R.id.button_reblog) as Button }
    private val favouriteButton: Button by lazy { itemView.f(R.id.button_favourite) as Button }
    private val translateButton: Button by lazy { itemView.f(R.id.button_translate) as Button }

    private lateinit var status: Status

    fun bind(status: Status) {
        this.status = status
        initBoosted(status)

        val target = status.reblog ?: status
        target.account?.let { account -> initAccount(account) }
        initContent(target)
        initActions(target)
        initMediaAttachments(target.mediaAttachments, target)
    }

    private fun initBoosted(status: Status) {
        if (status.reblog != null) {
            boostedLayout.visibility = View.VISIBLE
            boostedMessage.text = itemView.context.resources.getString(R.string.timeline_boosted).format(status.account?.preparedDisplayName)

        } else {
            boostedLayout.visibility = View.GONE
        }


        boostedLayout.visibility = if (status.reblog != null) View.VISIBLE else View.GONE
    }

    private fun initContent(status: Status) {
        contentHeader.text = status.content?.header
        contentBody.text =
                if (status.content?.translatedText.isNullOrEmpty()) {
                    status.content?.body?.asHtml()?.trim()
                } else {
                    status.content?.translatedText
                }
        contentBody.movementMethod = LinkMovementMethod.getInstance()

        if (status.content?.header.isNullOrEmpty()) {
            contentHeader.visibility = View.GONE
            contentBody.visibility = View.VISIBLE
            showWarningContentImage.visibility = View.GONE
        } else {
            contentHeader.visibility = View.VISIBLE
            contentBody.visibility = View.GONE
            showWarningContentImage.visibility = View.VISIBLE
            showWarningContentImage.setOnClickListener {
                contentBody.visibility = View.VISIBLE
                showWarningContentImage.visibility = View.GONE
            }
        }
    }

    private fun initAccount(account: Account) {
        accountName.text = account.preparedDisplayName

        val border = ContextCompat.getColor(itemView.context, R.color.content_border)
        Picasso.with(itemView.context)
                .load(account.avatar)
                .transform(AvatarTransformation(border))
                .into(avatarImage)
    }

    private fun initActions(status: Status) {
        reblogButton.background =
                if (status.reblogged) {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_reblog)
                } else {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_reblog_unreblog)
                }

        favouriteButton.background =
                if (status.favourited) {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_favourite)
                } else {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_star_unfavourite)
                }
    }

    private fun initMediaAttachments(mediaAttachments: List<Media>, status: Status) {
        if (mediaAttachments.isEmpty()) {
            imagesLayout.visibility = View.GONE
        } else {
            imagesLayout.visibility = View.VISIBLE
            images.forEach { it.visibility = View.GONE }

            if (status.sensitive) {
                sensitiveLayout.visibility = View.VISIBLE
                sensitiveLayout.setOnLongClickListener {
                    showImages(mediaAttachments)
                    sensitiveLayout.visibility = View.GONE
                    true
                }
            } else {
                showImages(mediaAttachments)
            }
        }
    }

    private fun showImages(mediaAttachments: List<Media>) {
        image1.layoutParams = image1.layoutParams.also { params ->
            (params as FlexboxLayout.LayoutParams).flexBasisPercent = if (mediaAttachments.size.odd) 1f else 0.49f
        }
        mediaAttachments
                .forEachIndexed { index, media ->
                    images[index].visibility = View.VISIBLE
                    Glide.with(itemView.context)
                            .load(media.previewUrl)
                            .fitCenter()
                            .into(images[index])
                }
    }


    private fun View.f(id: Int): View? = findViewById(id)

    private val Int.odd: Boolean
            get() = this % 2 == 1
}