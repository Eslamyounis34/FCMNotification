package com.example.fcmnotification.model

import com.example.fcmnotification.model.Notification

data class PushNotification(
    val notification: Notification,
    val to: String
)