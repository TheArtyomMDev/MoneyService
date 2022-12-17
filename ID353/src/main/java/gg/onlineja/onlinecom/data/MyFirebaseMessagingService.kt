package gg.onlineja.onlinecom.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.MainActivity

/*
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        println("NEW MESSAGE GOT FROM ${message.from}")
        println("NEW LINK : ${message.data["link"]}")

        val intent = Intent(this, MainActivity::class.java)

        var link = message.data["link"]
        link = "loans/10"

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("link", link)
       // intent.putExtra("order", order)
        startActivity(intent)
    }
}

 */