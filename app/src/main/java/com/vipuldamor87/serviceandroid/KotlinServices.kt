package com.vipuldamor87.serviceandroid

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class KotlinServices : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        //using thread will reduce lag in UI
        val thread = Thread {
            Log.d("TAG", "This service is running ")
        }
        thread.start()
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