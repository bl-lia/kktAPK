package com.bl_lia.kirakiratter.presentation.view_pager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class TimelineViewPager : ViewPager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onTouchEvent(ev: MotionEvent?): Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false
}
