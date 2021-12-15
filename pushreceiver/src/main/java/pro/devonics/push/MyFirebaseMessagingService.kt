package pro.devonics.push

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.HttpURLConnection
import java.net.URL


private const val TAG = "MyFirebaseMessagingService"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag")
    override fun handleIntent(intent: Intent) {
        //Log.d(TAG, "handleIntent = $intent")
        try {
            if (intent.extras != null) {
                val builder = RemoteMessage.Builder("MyFirebaseMessagingService")
                for (key in intent.extras!!.keySet()) {
                    builder.addData(key!!, intent.extras!![key].toString())
                }
                onMessageReceived(builder.build())
                //Log.d(TAG, "handleIntent 1")
            } else {
                super.handleIntent(intent)
                //Log.d(TAG, "handleIntent 2")
            }
        } catch (e: Exception) {
            super.handleIntent(intent)//if app close
            //Log.d(TAG, "handleIntent e: $e")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag", "UnspecifiedImmutableFlag", "ServiceCast")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //Log.d(TAG, "onMessageReceived")

        val packageName = applicationContext.packageName
        val mLauncher = "ic_launcher"
        val resId = resources.getIdentifier(mLauncher, "mipmap", packageName)
        //Log.d(TAG, "onMessageReceived resId: $resId")
        //Log.d(TAG, "onMessageReceived packageName: $packageName")

        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent?.putExtra("command", "transition")

        // Send pushData to intent
        intent?.putExtra("push_type", remoteMessage.data["push_type"])
        intent?.putExtra("push_id", remoteMessage.data["push_id"])

        //Log.d(TAG, "push_type: ${remoteMessage.data["push_type"]}")
        //Log.d(TAG, "push_id: ${remoteMessage.data["push_id"]}")
        //Log.d(TAG, "push_id: ${remoteMessage.data["push_id"]}")

        val image = remoteMessage
            .data["image"]?.let { getBitmapFromUrl(it) }

        //get icon
        val smallIcon = remoteMessage
            .notification?.imageUrl?.let { getBitmapFromUrl(it.toString()) }

        //Log.d(TAG, "image: $image")
        //Log.d(TAG, "smallIcon: $smallIcon")

        //Log.d(TAG, "image: ${remoteMessage.data["image"]}")
        //Log.d(TAG, "smallIcon: ${remoteMessage.notification?.imageUrl}")

        val rnds = (1..1000).random()
        val pendingIntent = PendingIntent.getActivity(
            this, rnds, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "Default"

        /*if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        } else {
            Log.d(TAG, "Message data payload else: ${remoteMessage.data}")
        }*/

        if (remoteMessage.data["image"] != null && remoteMessage.notification?.imageUrl == null) {
            val builder = NotificationCompat.Builder(this, channelId)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(resId)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                //.setContentText("https://www.google.com.ua/")
                //.setLargeIcon(largeIcon)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setChannelId(channelId)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    //.bigLargeIcon(largeIcon)
                )
                .setContentIntent(pendingIntent)

            val notificationManager = NotificationManagerCompat.from(this)
            /*val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Default channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(0, builder.build())
        }

        if (remoteMessage.data["image"] != null && remoteMessage.notification?.imageUrl != null) {

            val builder = NotificationCompat.Builder(this, channelId)
                //.setSmallIcon(R.mipmap.ic_launcher)
                .setSmallIcon(resId)
                .setContentTitle(remoteMessage.notification?.title)
                .setContentText(remoteMessage.notification?.body)
                //.setContentText("https://www.google.com.ua/")
                .setLargeIcon(smallIcon)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setChannelId(channelId)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(smallIcon)
                )
                .setContentIntent(pendingIntent)

            val notificationManager = NotificationManagerCompat.from(this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Default channel",
                    NotificationManager.IMPORTANCE_DEFAULT)
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(0, builder.build())
        }

        if (remoteMessage.data["image"] == null && remoteMessage.notification?.imageUrl != null) {
            remoteMessage.notification?.let {
                val builder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(resId)
                    .setContentTitle(remoteMessage.notification?.title)
                    .setContentText(remoteMessage.notification?.body)
                    .setLargeIcon(smallIcon)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setChannelId(channelId)
                .setContentIntent(pendingIntent)

                val notificationManager = NotificationManagerCompat.from(this)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        "Default channel",
                        NotificationManager.IMPORTANCE_DEFAULT)
                    notificationManager.createNotificationChannel(channel)
                }
                notificationManager.notify(0, builder.build())
                //Log.d(TAG, "Message Notification Body: ${it.body}")
                //Log.d(TAG, "Message Notification Body icon: ${it.icon}")
            }
        }

        if (remoteMessage.data["image"] == null
            && remoteMessage.notification?.imageUrl == null
            && remoteMessage.notification != null) {

            remoteMessage.notification?.let {
                val builder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(resId)
                    .setContentTitle(remoteMessage.notification?.title)
                    .setContentText(remoteMessage.notification?.body)
                    //.setLargeIcon(smallIcon)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setChannelId(channelId)
                    .setContentIntent(pendingIntent)

                val notificationManager = NotificationManagerCompat.from(this)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        channelId,
                        "Default channel",
                        NotificationManager.IMPORTANCE_DEFAULT)
                    notificationManager.createNotificationChannel(channel)
                }
                notificationManager.notify(0, builder.build())
                //Log.d(TAG, "Message Notification Body: ${it.body}")
                //Log.d(TAG, "Message Notification Body icon: ${it.icon}")
            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun getBitmapFromUrl(imageUrl: String): Bitmap {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput
        connection.connect()
        val input = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }

    @SuppressLint("LongLogTag")
    override fun onNewToken(p0: String) {
        //Log.d(TAG, "################ onNewToken##################: $p0")
        //super.onNewToken(p0)

    }

    private fun sendNotification() {

    }

    private fun sendNotificationWithImage() {

    }
}