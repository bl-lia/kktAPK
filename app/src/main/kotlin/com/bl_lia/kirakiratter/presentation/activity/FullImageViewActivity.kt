package com.bl_lia.kirakiratter.presentation.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.SharedElementCallback
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.adapter.full_image.ImagePagerAdapter
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_full_image.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File

@RuntimePermissions
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

    private var target: InsertMediaTarget? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_full_image_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.menu_save_image -> {
                    FullImageViewActivityPermissionsDispatcher.saveImageWithCheck(this)
                    return true
                }
                else -> return false
            }
        } else {
            return false
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImage() {
        imageUrls[pager_screenshot.currentItem].let { url ->
            val filename = Uri.parse(url).pathSegments.last()
            target = InsertMediaTarget(this, filename)
            Picasso.with(this)
                    .load(url)
                    .into(target)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        FullImageViewActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    class InsertMediaTarget(
            val context: Context,
            val filename: String): Target {
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            val dir = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}", "KiraKiratter")
            if (!dir.exists()) {
                dir.mkdir()
            }
            File(dir, filename).apply {
                when (extension.toLowerCase()) {
                    "jpg", "jpeg" -> Bitmap.CompressFormat.JPEG
                    "png"         -> Bitmap.CompressFormat.PNG
                    else          -> null
                }?.let { format ->
                    outputStream().also { out ->
                        bitmap?.compress(format, 100, out)
                        out.flush()
                        out.close()
                    }
                }
            }.let { file ->
                MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)
                Toast.makeText(context, context.getString(R.string.image_saved), Toast.LENGTH_LONG).show()
            }
        }
    }
}