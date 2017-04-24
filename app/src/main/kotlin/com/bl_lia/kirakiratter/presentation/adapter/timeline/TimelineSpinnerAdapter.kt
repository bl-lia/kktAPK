package com.bl_lia.kirakiratter.presentation.adapter.timeline

import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class TimelineSpinnerAdapter (
        private val ctx: Context,
        private val resourcId: Int,
        private val menus: List<String>
) : ArrayAdapter<String>(ctx, resourcId, menus) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getView(position, convertView, parent)
                .also { view ->
                    val textView: TextView = view.findViewById(R.id.text1) as TextView
                    textView.text = ""
                }
    }
}