package com.vipuldamor87.foregroundservice

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),111)
        }



        startForegroundService.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                Log.d("Oreo","This works")
                startForegroundService(Intent(this,MyForeService::class.java))
            }
            else
            {
                startService(Intent(this,MyForeService::class.java))
            }
        }
        startGettingLocation.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                Log.d("Oreo","This works")
                startForegroundService(Intent(this,MyLocationForeService::class.java))
            }
            else
            {
                startService(Intent(this,MyLocationForeService::class.java))
            }
        }

    }
}