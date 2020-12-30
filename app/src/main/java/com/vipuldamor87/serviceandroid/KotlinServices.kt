package com.vipuldamor87.serviceandroid

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ActivityCompat
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.thread

class KotlinServices : Service() {
    lateinit var lm : LocationManager
    lateinit var loc: Location

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
      try{ loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)}
       catch(e :Exception)
       {
           Log.d("error","not working")
       }
        /*
        var ll = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                revereGeoCode(location)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onProviderEnabled(provider: String?) {
                TODO("Not yet implemented")
            }

            override fun onProviderDisabled(provider: String?) {
                TODO("Not yet implemented")
            }

        }*/
        onTaskRemoved(intent)
        //using thread will reduce lag in UI
        Log.d("TAG", "This service is running ")
        revereGeoCode(loc)
        return START_STICKY
        //return START_REDELIVER_INTENT to reinstatiate the service when application is removed from recents
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


    private fun revereGeoCode(loc: Location?) {
        var gc = Geocoder(this, Locale.getDefault())
        var addresses = gc.getFromLocation(loc!!.latitude,loc!!.longitude,2)
        var address = addresses.get(0)
        Toast.makeText(applicationContext,"${address.locality}",Toast.LENGTH_LONG).show()
        Log.d("Mylocation","${address.locality}")
    }

}