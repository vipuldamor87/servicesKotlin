package com.vipuldamor87.foregroundservice.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_broadcast.*
import java.lang.Exception

class Broadcast : AppCompatActivity() {
    var receiver: BroadcastReceiver? = null
    lateinit var result:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)

        var int1 =integer1.text
        var int2 = integer2.text
        try{
            result= intent.getStringExtra("message").toString()
        resultS.setText(result)
        }
        catch (e:Exception){}

        sendBroadcast.setOnClickListener {

            val i = Intent(Intent(applicationContext,Broadcast_receive::class.java))
            i.putExtra("int1","$int1")
            i.putExtra("int2","$int2")
            startActivity(i)

        }

    }

}