package com.bl_lia.kirakiratter.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.SharedElementCallback
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.adapter.full_image.ImagePagerAdapter
import kotlinx.android.synthetic.main.activity_full_image.*

class FullImageViewActivity : AppCompatActivity() {

    companion object {
        fun newIntent(fromActivity: Activity, imageUrls: ArrayList<String>, previewUrls: ArrayList<String>, defaultPosition: Int = 0): Intent =
                Intent(fromActivity, FullImageViewActivity::class.java).apply {
                    putExtra(INTENT_EXTRA_IMAGE_URLS, imageUrls)
                    putExtra(INTENT_EXTRA_PREVIEW_URLS, previewUrls)
                    putExtra(INTENT_EXTRA_DEFAULT_POSITION, defaultPosition)
                }

        private const val INTENT_EXTRA_IMAGE_URLS = "image_urls"
        private const val INTENT_EXTRA_PREVIEW_URLS = "preview_urls"
        private const val INTENT_EXTRA_DEFAULT_POSITION = "default_position"
    }

    private val imageUrls: ArrayList<String> by lazy {
        intent.getStringArrayListExtra(INTENT_EXTRA_IMAGE_URLS)
    }

    private val previewUrls: ArrayList<String> by lazy {
        intent.getStringArrayListExtra(INTENT_EXTRA_PREVIEW_URLS)
    }

    private val defaultPosition: Int by lazy {
        intent.getIntExtra(INTENT_EXTRA_DEFAULT_POSITION, 0)
    }

    private val pagerAdapter: ImagePagerAdapter by lazy {
        ImagePagerAdapter(this, imageUrls, previewUrls)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        pager_screenshot.adapter = pagerAdapter
        pager_screenshot.currentItem = defaultPosition

        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                super.onMapSharedElements(names, sharedElements)
                pagerAdapter.currentImageView?.let { view ->
                    sharedElements?.put("image", view)
                }
            }
        })
    }
}