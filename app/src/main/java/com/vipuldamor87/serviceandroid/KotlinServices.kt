package com.vipuldamor87.serviceandroid

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.lang.Thread.sleep

class KotlinServices : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)

                Log.d("TAG", "This service is running ")

        return START_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val restartServiceIntent = Intent(applicationContext, this.javaClass)
        restartServiceIntent.setPackage(packageName)
        startService(restartServiceIntent)
        super.onTaskRemoved(rootIntent)
    }
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "MyService Completed or Stopped.", Toast.LENGTH_SHORT).show()
        //while this is getting printed the service is still running in the background
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}