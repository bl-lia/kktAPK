package com.bl_lia.kirakiratter.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bl_lia.kirakiratter.R
import kotlinx.android.synthetic.main.activity_license.*

class ThanksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
    }

    override fun onStart() {
        super.onStart()
        content_web.loadUrl("file:///android_asset/thanks.html")
    }
}