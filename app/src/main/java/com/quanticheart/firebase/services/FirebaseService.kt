package com.quanticheart.firebase.services

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.quanticheart.core.notification.notify
import com.quanticheart.firebase.R
import com.quanticheart.firebase.ui.main.MainActivity

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

        val isDeeplink = p0.data.containsKey("deeplink")

        val intent =
            if (isDeeplink) Intent(Intent.ACTION_VIEW, Uri.parse(p0.data["deeplink"])) else Intent(
                this,
                MainActivity::class.java
            )

        val title = p0.data["title"] ?: p0.notification?.title ?: this.getString(R.string.app_name)
        val message = p0.data["message"] ?: p0.notification?.body ?: ""

        val channelID = this.getString(R.string.default_notification_channel_id)
        val channelName = this.getString(R.string.default_notification_channel_name)
        val channelDescription = this.getString(R.string.default_notification_channel_description)

        notify()
            .setTitle(title)
            .setMessage(message)
            .setChannelId(channelID)
            .setChannelName(channelName)
            .setChannelDescription(channelDescription)
            .setAction(intent)
            .show()

    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.i("TOKEN_FIREBASE", p0)
    }
}
