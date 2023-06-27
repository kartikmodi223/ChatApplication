package com.example.chatapplication


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val channelID = "notification channel"
    private val channelName = "com.example.chatapplication"
 

    override fun onMessageReceived(message: RemoteMessage) {
        generatenotification(message.notification!!.title!!,message.notification!!.body!!)
    }


    private fun getRemoteView(title: String, messege: String): RemoteViews {
        val RemoteView =
            RemoteViews("com.example.application", R.layout.activity_messege_service)
        RemoteView.setTextViewText(R.id.title, title)
        RemoteView.setTextViewText(R.id.messege, messege)
        RemoteView.setImageViewResource(R.id.notification, R.drawable.ic_baseline_message_24)

        return RemoteView
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun generatenotification(title : String, messege :String){
        val intent = Intent(this,MyFirebaseMessagingService::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        var builder :NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,channelID)
            .setSmallIcon(R.drawable.ic_baseline_message_24).setAutoCancel(true).setVibrate(
                longArrayOf(1000,1000,1000,1000)).setOnlyAlertOnce(true).setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,messege))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build()
        )
    }

}
