package com.bl_lia.kirakiratter.presentation.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.TaskStackBuilder
import android.support.v7.app.NotificationCompat
import android.util.Log
import com.bl_lia.kirakiratter.App
import com.bl_lia.kirakiratter.R
import com.bl_lia.kirakiratter.presentation.activity.MainActivity
import com.bl_lia.kirakiratter.presentation.internal.di.component.DaggerMessagingServiceComponent
import com.bl_lia.kirakiratter.presentation.internal.di.component.MessagingServiceComponent
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import javax.inject.Inject

class FetchNotificationJobService : JobService() {

    @Inject
    lateinit var extra: FetchNotificationJobServiceExtra

    private val component: MessagingServiceComponent by lazy {
        DaggerMessagingServiceComponent.builder()
                .applicationComponent((application as App).component)
                .build()
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        component.inject(this)

        job?.extras?.getString("notification_id")?.let { id ->
            extra.getNotification(id.toInt())
                    .subscribe { notification, error ->
                        if (error != null) {
                            return@subscribe
                        }

                        val builder = NotificationCompat.Builder(applicationContext).also { builder ->
                            builder.setSmallIcon(R.drawable.ic_kirakiratter_logo)
                            builder.setContentTitle(notification.notifiedMessage(applicationContext))
                            builder.setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                            notification.status?.let { status ->
                                builder.setContentText(status.content.body?.trim())
                            }

                            val resultIntent = Intent(this, MainActivity::class.java)
                            val stackBuilder = TaskStackBuilder.create(this).apply {
                                addParentStack(MainActivity::class.java)
                                addNextIntent(resultIntent)
                            }
                            val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                            builder.setContentIntent(resultPendingIntent)
                        }

                        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.notify(100, builder.build())
                    }
        }

        return false
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }
}