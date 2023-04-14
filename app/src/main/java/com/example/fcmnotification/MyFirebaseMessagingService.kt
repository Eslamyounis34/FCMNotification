package com.example.fcmnotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : com.google.firebase.messaging.FirebaseMessagingService() {


//    generate the notification
//    attach the notification created with custom layout
//    show the notification

    val channelId = "notification_channel"
    val channelName = "com.example.fcmnotification"


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data != null) {
            generateNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        }

    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, content: String): RemoteViews {
        val remoteView = RemoteViews("com.example.fcmnotification", R.layout.notification_layout)

        remoteView.setTextViewText(R.id.notification_title, title)
        remoteView.setTextViewText(R.id.notification_content, content)
        remoteView.setImageViewResource(R.id.app_Logo, R.drawable.notification_icon)

        return remoteView


    }

    fun generateNotification(title: String, content: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext,
            channelId
        ).setSmallIcon(R.drawable.notification_icon)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, content))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())

    }


}