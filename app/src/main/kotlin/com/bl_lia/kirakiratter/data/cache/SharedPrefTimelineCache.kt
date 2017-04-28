package com.bl_lia.kirakiratter.data.cache

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefTimelineCache
    @Inject constructor(
            private val sharedPreferences: SharedPreferences
    ): TimelineCache {

    override var selectedTimeline: String?
        get() = sharedPreferences.getString("selectedTimeline", null)
        set(value) {
            sharedPreferences.edit().apply {
                putString("selectedTimeline", value)
            }.apply()
        }
}