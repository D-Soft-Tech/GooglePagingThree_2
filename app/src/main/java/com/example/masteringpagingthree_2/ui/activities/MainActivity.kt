package com.example.masteringpagingthree_2.ui.activities // ktlint-disable package-name

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.masteringpagingthree_2.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "FIRE_BASE_TOKEN"
const val TAG1 = "FIRE_BASE_TOKEN_1"
const val TAG2 = "FIRE_BASE_TOKEN_2"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG1, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                sendTokenToBackend(token)
                /*
                dkFbo9IKSdq8ZLp2flNuur:APA91bHlKbJ2UpdZUPomOOM7lqTM3VIKKsO0_MRv6OY_3PNJqPnB0LaE0OqUlJTDuVixO3ydgx_THfLiRx8yzLab3t58RUUY01uX468Koi1fB7nJmW0K1gQvWGUb49X51om7WEw0AMei

                epgEikn-S5uBLF3aigYIDT:APA91bGWAhaJOo6dRUDOXyy087_D47gHQWzc4Q0CXeWSLY7cpwzYIMAfAZD3i40LyIQ1qgVVVSrm3pCpY22kYDFOghMAmWQvENn6gj7rta499emByVXDLVFHK6AHmxAEEy69h8fTBs2X
                 */
            }
        )
    }

    private fun sendTokenToBackend(token: String) {

    }
}
