package com.vipuldamor87.foregroundservice

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

public class MyForeService : Service() {
    /*private lateinit var nManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel*/
    private val CHANNEL_ID = "ForegroundService Kotlin"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
        val intent1 = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("MyForeground_service")
            .setContentText("HelloNotification")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent).build()
        startForeground(1,notification)


        return START_STICKY
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "ForegroundNotification", NotificationManager.IMPORTANCE_DEFAULT
            )
           val nManager = getSystemService(NotificationManager::class.java)
            nManager.createNotificationChannel(notificationChannel)
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }
}