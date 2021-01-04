package com.vipuldamor87.foregroundservice.Services

import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.vipuldamor87.foregroundservice.MainActivity
import com.vipuldamor87.foregroundservice.R
import java.util.*

public class MyLocationForeService : Service() {
    lateinit var lm : LocationManager
    lateinit var loc: Location
    /*private lateinit var nManager : NotificationManager
    private lateinit var notificationChannel : NotificationChannel*/
    private val CHANNEL_ID = "ForegroundService Kotlin"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try{ loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)}
        catch(e :Exception)
        {
            Log.d("error","not working")
        }

        Thread(Runnable {
            while(true)
            {
                createNotificationChannel()
            startDisplayinNotification()
            Thread.sleep(60000)
            Log.d("Thread", "Location updated")
            }
        }).start()


        return START_STICKY
    }

    private fun startDisplayinNotification() {
        val intent1 = Intent(this, MainActivity::class.java)
        var address = revereGeoCode(loc)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Your Location")
            .setContentText(address)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent).build()
        startForeground(1,notification)
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

    private fun revereGeoCode(loc: Location?): String {
        var gc = Geocoder(this, Locale.getDefault())
        var addresses = gc.getFromLocation(loc!!.latitude,loc!!.longitude,2)
        var address = addresses.get(0)
        return "${address.locality}"
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