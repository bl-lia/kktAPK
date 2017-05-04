package com.bl_lia.kirakiratter.presentation.adapter.full_image

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

class ImagePagerAdapter(
        private val context: Context,
        private val imageUrls: List<String>,
        private val previewUrls: List<String>
) : PagerAdapter() {

    var currentImageView: ImageView? = null
        private set

    override fun getCount(): Int = imageUrls.size

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val imageView: ImageView = PhotoView(context).apply {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        val previewReq = Glide.with(context).load(previewUrls[position])

        Glide.with(context)
                .load(imageUrls[position])
                .thumbnail(previewReq)
                .into(imageView)
        container?.addView(imageView)
        return imageView
    }

    override fun isViewFromObject(view: View?, obj: Any?): Boolean =
            view == obj as ImageView

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as ImageView)
    }

    override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.setPrimaryItem(container, position, `object`)
        currentImageView = `object` as ImageView
    }
}