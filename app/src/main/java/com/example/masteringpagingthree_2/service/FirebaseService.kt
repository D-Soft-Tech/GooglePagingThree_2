package com.example.masteringpagingthree_2.service // ktlint-disable package-name

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import com.example.masteringpagingthree_2.R
import com.example.masteringpagingthree_2.data.model.TransactionResponse
import com.example.masteringpagingthree_2.ui.activities.MainActivity
import com.example.masteringpagingthree_2.util.AppConstants.TAG_NEW_TOKEN_RECEIVED
import com.example.masteringpagingthree_2.util.AppConstants.TAG_NOTIFICATION_RECEIVED
import com.example.masteringpagingthree_2.util.AppConstants.TAG_NOTIFICATION_RECEIVED_2
import com.example.masteringpagingthree_2.util.RandomPurposeUtil.formatCurrencyAmount
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class FirebaseService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG_NOTIFICATION_RECEIVED, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            val getKey = remoteMessage.data["TransactionNotification"]
            Log.d(TAG_NOTIFICATION_RECEIVED, "Message data payload: ${remoteMessage.data}")
            Log.d(TAG_NOTIFICATION_RECEIVED_2, "Message data Key: $getKey")

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }
        }

        // Check if message contains a notification payload.
//        remoteMessage.notification?.let {
//            Log.d(TAG_NOTIFICATION_RECEIVED, "Message Notification Body: ${it.body}")
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        remoteMessage.data["TransactionNotification"]?.let {
            val transaction = Gson().fromJson(it, TransactionResponse::class.java)
            val transactionAmount = transaction.amount ?: 0
            sendNotification("${transactionAmount.formatCurrencyAmount()} Received \nFrom: ${transaction.depositorAccountName}   (${transaction.depositorBankName})")
        }
    }

    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0 /* Request code */,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = Notification.Builder(this, channelId)
            .setContentTitle(getString(R.string.transacion_received))
            .setStyle(Notification.BigTextStyle().bigText(messageBody))
            .setSmallIcon(R.drawable.unsplash)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.transacion_received),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun sendRegistrationToServer(token: String?) {
        // Implement this method to send token to your app server.
        Log.d(TAG_NEW_TOKEN_RECEIVED, "sendRegistrationTokenToServer($token)")
    }

    /*
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance(this)
            .beginWith(work)
            .enqueue()
        // [END dispatch_job]
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }
    */
}
