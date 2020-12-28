package com.vipuldamor87.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MyAndroidServicce::class.java)
        buttonStart.setOnClickListener() {
            Intent(this, MyKotlinService::class.java).also { intent ->
                startService(intent)

            }

            buttonStop.setOnClickListener() {
                stopService(Intent(intent))
            }

        }
    }
}