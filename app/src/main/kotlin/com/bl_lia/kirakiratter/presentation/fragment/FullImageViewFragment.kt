package com.bl_lia.kirakiratter.presentation.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.bl_lia.kirakiratter.R
import com.squareup.picasso.Picasso

class FullImageViewFragment : DialogFragment() {

    companion object {
        fun newInstance(imageUrls: ArrayList<String>, defaultPosition: Int = 0): FullImageViewFragment {
            val params = Bundle().apply {
                putStringArrayList(PARAM_IMAGE_URL, imageUrls)
                putInt(PARAM_DEFAULT_POSITION, defaultPosition)
            }
            return FullImageViewFragment().apply {
                arguments = params
            }
        }

        private const val PARAM_IMAGE_URL = "image_url"
        private const val PARAM_DEFAULT_POSITION = "default_position"
    }

    private val imageUrls: List<String> by lazy {
        arguments.getStringArrayList(PARAM_IMAGE_URL).toList()
    }

    private val defaultPosition: Int by lazy {
        arguments.getInt(PARAM_DEFAULT_POSITION)
    }

    private val pagerAdapter: ImagePagerAdapter by lazy {
        ImagePagerAdapter(activity, imageUrls)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.fragment_full_image_view, null)
        initView(view)

        return Dialog(activity).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(view)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun initView(view: View) {
        val pagerScreenshot = view.findViewById(R.id.pager_screenshot) as ViewPager
        pagerScreenshot.adapter = pagerAdapter
        pagerScreenshot.currentItem = defaultPosition
    }

    internal class ImagePagerAdapter(
            private val context: Context,
            private val imageUrls: List<String>
    ) : PagerAdapter() {

        override fun getCount(): Int = imageUrls.size

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val imageView: ImageView = ImageView(context).apply {
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }

            Picasso.with(context).load(imageUrls[position]).into(imageView)
            container?.addView(imageView)
            return imageView
        }

        override fun isViewFromObject(view: View?, obj: Any?): Boolean =
                view == obj as ImageView

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as ImageView)
        }
    }
}