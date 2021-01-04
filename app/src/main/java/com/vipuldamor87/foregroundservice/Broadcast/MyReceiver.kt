package com.vipuldamor87.foregroundservice.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = "Broadcast intent detected " + intent.action
        val result:String = intent.getStringExtra("result")
        Toast.makeText(context, result,
            Toast.LENGTH_LONG).show()

    }
}