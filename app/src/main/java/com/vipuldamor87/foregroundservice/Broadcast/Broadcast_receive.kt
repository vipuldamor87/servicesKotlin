package com.vipuldamor87.foregroundservice.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_broadcast_receive.*

class Broadcast_receive : AppCompatActivity() {
    var receiver: BroadcastReceiver? = null
    lateinit var result:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receive)
        configureReceiver()

        val integer1:Int = intent.getStringExtra("int1").toInt()
        val integer2:Int = intent.getStringExtra("int2").toInt()
      // Toast.makeText(applicationContext,"number received are $integer1 $integer2",Toast.LENGTH_LONG).show()
       addition.setOnClickListener {
           result= (integer1+integer2).toString()
           broadcastIntent()
       }
        sub.setOnClickListener {
            result= (integer1-integer2).toString()
            broadcastIntent()
        }
        multi.setOnClickListener {
            result= (integer1*integer2).toString()
            broadcastIntent()
        }
        division.setOnClickListener{
            result= (integer1/integer2).toString()
            broadcastIntent()
        }
    }

    private fun broadcastIntent() {

        val intent = Intent()
        intent.putExtra("result","$result")
        intent.action = "com.vipuldamor87.foregroundservice"
        intent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        sendBroadcast(intent)
    }


    private fun configureReceiver() {
        val filter = IntentFilter()
        filter.addAction("com.vipuldamor87.foregroundservice")
        receiver = MyReceiver()
        registerReceiver(receiver, filter)
    }
}