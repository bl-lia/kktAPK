package com.bl_lia.kirakiratter.presentation.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = MainFragment.newInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(android.R.id.content, fragment)
            transaction.commit()
        }
    }
}
