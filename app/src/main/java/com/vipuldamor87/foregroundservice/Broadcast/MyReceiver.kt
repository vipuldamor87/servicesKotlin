package com.vipuldamor87.foregroundservice.Broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
       // val message = "Broadcast intent detected " + intent.action
        val result:String = intent.getStringExtra("result")
        Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        val i = Intent(context, Broadcast::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.putExtra("message", "$result")
        context.startActivity(i)

    }
}