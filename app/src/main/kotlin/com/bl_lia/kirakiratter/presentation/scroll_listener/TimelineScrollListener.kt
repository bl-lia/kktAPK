package com.bl_lia.kirakiratter.presentation.scroll_listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class TimelineScrollListener(
        private val layoutManger: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    companion object {
        const val VISIBLE_THRESHOLD: Int = 5
    }

    abstract fun onLoadMore()

    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0


    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        recyclerView?.let {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManger.itemCount
            firstVisibleItem = layoutManger.findFirstVisibleItemPosition()
        }

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            onLoadMore()
        }
    }
}