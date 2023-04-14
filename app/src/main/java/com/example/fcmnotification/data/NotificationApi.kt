package com.example.fcmnotification.data

import com.example.fcmnotification.Constants.CONTENT_TYPE
import com.example.fcmnotification.Constants.SERVER_KEY
import com.example.fcmnotification.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @Headers("Authorization: key=$SERVER_KEY","Content-Type: $CONTENT_TYPE")
    @POST("fcm/send")
    fun pushNotification(
        @Body pushNotification: PushNotification
    ): Response<ResponseBody>

}