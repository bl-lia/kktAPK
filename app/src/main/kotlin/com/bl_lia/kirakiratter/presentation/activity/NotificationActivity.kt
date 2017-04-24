package com.bl_lia.kirakiratter.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.fragment.NotificationFragment

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().also { transaction ->
                val fragment = NotificationFragment.newInstance()
                transaction.replace(android.R.id.content, fragment)
            }.commit()
        }
    }
}