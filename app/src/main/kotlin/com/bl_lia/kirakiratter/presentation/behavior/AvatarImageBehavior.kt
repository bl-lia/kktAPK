package com.bl_lia.kirakiratter.presentation.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import com.bl_lia.kirakiratter.R

class AvatarImageBehavior(private val mContext: Context, attrs: AttributeSet?) : CoordinatorLayout.Behavior<ImageView>() {

    private var mCustomFinalYPosition: Float = 0.toFloat()
    private var mCustomStartXPosition: Float = 0.toFloat()
    private var mCustomStartToolbarPosition: Float = 0.toFloat()
    private var mCustomStartHeight: Float = 0.toFloat()
    private var mCustomFinalHeight: Float = 0.toFloat()

    private var mAvatarMaxSize: Float = 0.toFloat()
    private val mFinalLeftAvatarPadding: Float
    private val mStartPosition: Float = 0.toFloat()
    private var mStartXPosition: Int = 0
    private var mStartToolbarPosition: Float = 0.toFloat()
    private var mStartYPosition: Int = 0
    private var mFinalYPosition: Int = 0
    private var mStartHeight: Int = 0
    private var mFinalXPosition: Int = 0
    private var mChangeBehaviorPoint: Float = 0.toFloat()

    init {

        if (attrs != null) {
            val a = mContext.obtainStyledAttributes(attrs, R.styleable.AvatarImageBehavior)
            mCustomFinalYPosition = a.getDimension(R.styleable.AvatarImageBehavior_finalYPosition, 0f)
            mCustomStartXPosition = a.getDimension(R.styleable.AvatarImageBehavior_startXPosition, 0f)
            mCustomStartToolbarPosition = a.getDimension(R.styleable.AvatarImageBehavior_startToolbarPosition, 0f)
            mCustomStartHeight = a.getDimension(R.styleable.AvatarImageBehavior_startHeight, 0f)
            mCustomFinalHeight = a.getDimension(R.styleable.AvatarImageBehavior_finalHeight, 0f)

            a.recycle()
        }

        init()

        mFinalLeftAvatarPadding = mContext.resources.getDimension(
                R.dimen.spacing_normal)
    }

    private fun init() {
        bindDimensions()
    }

    private fun bindDimensions() {
        mAvatarMaxSize = mContext.resources.getDimension(R.dimen.image_width)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: ImageView?, dependency: View?): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: ImageView?, dependency: View?): Boolean {
        if (child != null && dependency != null) {
            maybeInitProperties(child, dependency)
        }

        val maxScrollDistance = mStartToolbarPosition.toInt()
        val expandedPercentageFactor = dependency!!.y / maxScrollDistance

        if (expandedPercentageFactor < mChangeBehaviorPoint) {
            val heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint

            val distanceXToSubtract = (mStartXPosition - mFinalXPosition) * heightFactor + child!!.height / 2
            val distanceYToSubtract = (mStartYPosition - mFinalYPosition) * (1f - expandedPercentageFactor) + child.height / 2

            child.x = mStartXPosition - distanceXToSubtract
            child.y = mStartYPosition - distanceYToSubtract

            val heightToSubtract = (mStartHeight - mCustomFinalHeight) * heightFactor

            val lp = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.width = (mStartHeight - heightToSubtract).toInt()
            lp.height = (mStartHeight - heightToSubtract).toInt()
            child.layoutParams = lp
        } else {
            val distanceYToSubtract = (mStartYPosition - mFinalYPosition) * (1f - expandedPercentageFactor) + mStartHeight / 2

            child!!.x = (mStartXPosition - child.width / 2).toFloat()
            child.y = mStartYPosition - distanceYToSubtract

            val lp = child.layoutParams as CoordinatorLayout.LayoutParams
            lp.width = mStartHeight.toInt()
            lp.height = mStartHeight.toInt()
            child.layoutParams = lp
        }
        return true
    }

    private fun maybeInitProperties(child: ImageView, dependency: View) {
        if (mStartYPosition == 0)
            mStartYPosition = dependency.y.toInt()

        if (mFinalYPosition == 0)
            mFinalYPosition = dependency.height / 2

        if (mStartHeight == 0)
            mStartHeight = child.height

        if (mStartXPosition == 0)
            mStartXPosition = (child.x + child.width / 2).toInt()

        if (mFinalXPosition == 0)
            mFinalXPosition = mContext.resources.getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + mCustomFinalHeight.toInt() / 2

        if (mStartToolbarPosition == 0f)
            mStartToolbarPosition = dependency.y

        if (mChangeBehaviorPoint == 0f) {
            mChangeBehaviorPoint = (child.height - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition))
        }
    }

    val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = mContext.resources.getIdentifier("status_bar_height", "dimen", "android")

            if (resourceId > 0) {
                result = mContext.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    companion object {

        private val MIN_AVATAR_PERCENTAGE_SIZE = 0.3f
        private val EXTRA_FINAL_AVATAR_PADDING = 80

        private val TAG = "behavior"
    }
}