package com.bl_lia.kirakiratter.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

class OutsideClickableScrollView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    var onClickOutsideListener: (() -> Unit)? = null

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if(ev?.actionMasked == MotionEvent.ACTION_UP) {
            val isOutside =
                    if(childCount == 0) true
                    else getChildAt(0).let { child -> ev.y + scrollY < child.top || ev.y + scrollY > child.bottom }
            if(isOutside) {
                post { onClickOutsideListener?.invoke() }
            }
        }

        return super.onTouchEvent(ev)
    }
}