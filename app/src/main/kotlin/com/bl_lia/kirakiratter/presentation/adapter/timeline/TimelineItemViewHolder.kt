package com.bl_lia.kirakiratter.presentation.adapter.timeline

import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
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

    val onClickMoreMenu = Observable.create<Pair<Status, View>> { subscriber ->
        moreMenuButton.setOnClickListener {
            subscriber.onNext(Pair(status, moreMenuButton))
        }
    }

    val onClickImage = Observable.create<Triple<Status, Int, ImageView>> { subscriber ->
        image1.setOnClickListener { subscriber.onNext(Triple(status, 0, image1)) }
        image2.setOnClickListener { subscriber.onNext(Triple(status, 1, image2)) }
        image3.setOnClickListener { subscriber.onNext(Triple(status, 2, image3)) }
        image4.setOnClickListener { subscriber.onNext(Triple(status, 3, image4)) }
    }

    // Boosted
    private val boostedLayout: ViewGroup by lazy { itemView.f(R.id.timeline_boosted) as ViewGroup }
    private val boostedMessage: TextView by lazy { itemView.f(R.id.text_boosted) as TextView }

    // Account
    private val avatarImage: ImageView by lazy { itemView.f(R.id.image_avatar) as ImageView }
    private val accountName: TextView by lazy { itemView.f(R.id.text_account_name) as TextView }
    private val userName: TextView by lazy { itemView.f(R.id.text_user_name) as TextView }

    // Content
    private val contentHeader: TextView by lazy { itemView.f(R.id.text_content_header) as TextView }
    private val contentBody: TextView by lazy { itemView.f(R.id.text_content_body) as TextView }
    private val showWarningContentImage: ImageView by lazy { itemView.f(R.id.image_content_show) as ImageView }
    private val hideWarningContentImage: ImageView by lazy { itemView.f(R.id.image_content_hide) as ImageView }

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
    private val moreMenuButton: Button by lazy { itemView.f(R.id.button_more_menu) as Button }

    // Time
    private val tootTime: TextView by lazy { itemView.f(R.id.text_toot_time) as TextView }

    private lateinit var status: Status

    fun bind(status: Status, simpleMode: Boolean = false) {
        this.status = status
        initBoosted(status)

        val target = status.reblog ?: status
        tootTime.visibility = if (simpleMode) View.GONE else View.VISIBLE
        tootTime.text = DateUtils.getRelativeTimeSpanString(status.createdAt?.time!!)
        target.account?.let { account -> initAccount(account, simpleMode) }
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
            hideWarningContentImage.visibility = View.GONE
        } else {
            contentHeader.visibility = View.VISIBLE
            contentBody.visibility = View.GONE
            showWarningContentImage.visibility = View.VISIBLE
            hideWarningContentImage.visibility = View.GONE
            showWarningContentImage.setOnClickListener {
                contentBody.visibility = View.VISIBLE
                showWarningContentImage.visibility = View.GONE
                hideWarningContentImage.visibility = View.VISIBLE
            }
            hideWarningContentImage.setOnClickListener {
                contentBody.visibility = View.GONE
                showWarningContentImage.visibility = View.VISIBLE
                hideWarningContentImage.visibility = View.GONE
            }
        }
    }

    private fun initAccount(account: Account, simpleMode: Boolean) {
        accountName.text = account.preparedDisplayName
        userName.visibility = if (simpleMode) View.GONE else View.VISIBLE
        userName.text = "@${account.userName}"

        Picasso.with(itemView.context)
                .load(account.avatar)
                .transform(AvatarTransformation(itemView.context, R.color.content_border))
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
            sensitiveLayout.visibility = View.GONE
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