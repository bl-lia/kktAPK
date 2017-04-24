package com.bl_lia.kirakiratter.presentation.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.fragment.KatsuFragment

class KatsuActivity : AppCompatActivity() {

    companion object {
        val INTENT_PARAM_REPLY_ACCOUNT_NAME = "intent_param_reply_account_name"
        val INTENT_PARAM_REPLY_STATUS_ID = "intent_param_reply_status_id"
        val INTENT_PARAM_SHARED_TEXT = "intent_param_shared_text"
        val INTENT_PARAM_SHARED_IMAGE = "intent_param_shared_image"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().also { transaction ->
                val replyAccountName = intent.getStringExtra(INTENT_PARAM_REPLY_ACCOUNT_NAME)
                val replyStatusId = intent.getIntExtra(INTENT_PARAM_REPLY_STATUS_ID, -1)
                val sharedText = intent.getStringExtra(INTENT_PARAM_SHARED_TEXT)
                val sharedImage = intent.getParcelableExtra<Uri>(INTENT_PARAM_SHARED_IMAGE)
                val fragment = KatsuFragment.newInstance(
                        accountName = replyAccountName,
                        replyStatusId = replyStatusId,
                        sharedText = sharedText,
                        sharedImage = sharedImage)
                transaction.replace(android.R.id.content, fragment)
            }.commit()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, TimelineActivity::class.java).apply {
            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }
}