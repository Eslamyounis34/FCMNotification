package com.example.fcmnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fcmnotification.data.RetrofitInstance
import com.example.fcmnotification.databinding.ActivityMainBinding
import com.example.fcmnotification.model.Notification
import com.example.fcmnotification.model.PushNotification
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "MyActivity"
    lateinit var binding: ActivityMainBinding
    var token=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->

            // Get new FCM registration token
            val token = task.result
            binding.token.setText(token.toString())

        })

        binding.sendBt.setOnClickListener {
            var title = binding.title.text
            var message = binding.message.text

            if (title.isNotEmpty() && message.isNotEmpty()) {

                var notificationParams = Notification(message.toString(), title.toString())
                var pushNotification =
                    PushNotification(notificationParams, binding.token.text.toString())
                sendNotification(pushNotification)
            }
        }


    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.pushNotification(notification)
                if (response.isSuccessful) {
                    Log.d(TAG, "NotificationResponse: ${response.toString()}")
                } else {
                    Log.e(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
}