package com.vipuldamor87.foregroundservice.Connectivity

import android.content.Context
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_connectivity.*

class Connectivity : AppCompatActivity(),ConnectivityListener.ConnectivityReceiverListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connectivity)
        registerReceiver(ConnectivityListener(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityListener.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(ConnectivityListener())
    }
    private fun showNetworkMessage(isConnected: Boolean) {

        if (!isConnected) {
            displayNetwork.setText("No internet Connection")
            linearLayout.setBackgroundColor(Color.RED)
        }
        if(isConnected)
        {
            displayNetwork.setText("Connected to internet")
            linearLayout.setBackgroundColor(Color.GREEN)
        }
    }

}