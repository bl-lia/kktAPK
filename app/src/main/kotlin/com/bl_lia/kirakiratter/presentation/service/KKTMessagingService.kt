package com.bl_lia.kirakiratter.presentation.service

import android.os.Bundle
import android.util.Log
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class KKTMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage?) {
        Log.d("TAGTAG", message?.data.toString())

        message?.data?.get("notification_id")?.let { id ->
            scheduledJob(id)
        }
    }

    private fun scheduledJob(id: String) {
        val bundle = Bundle().apply {
            putString("notification_id", id)
        }
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val job =
                dispatcher.newJobBuilder()
                        .setService(FetchNotificationJobService::class.java)
                        .setTag("fetch_notification")
                        .setExtras(bundle)
                        .build()
        dispatcher.schedule(job)
    }
}