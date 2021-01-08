package com.vipuldamor87.foregroundservice.ApiCalls

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_retrofit.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Retrofit : AppCompatActivity() {

    lateinit var  adapter : DataAdapter
    // val BaseUrl ="https://reqres.in/api/users?page=2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        getData()

    }

    private fun showsnack() {
        val snack = Snackbar.make(layout1,"No Internet Connection",Snackbar.LENGTH_INDEFINITE)
        //snack.setBackgroundTint(Color.RED)
        snack.setAction("Retry", View.OnClickListener {
                       showsnack()
        })
        snack.show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }


    private fun getData() {
        if(isNetworkAvailable()) {
            val data = Service.serviceInterface.getData()
            data.enqueue(object : retrofit2.Callback<dataService> {
                override fun onResponse(call: Call<dataService>, response: Response<dataService>) {

                    val data = response.body()
                    if (data != null) {
                        Log.d("mytag", data.toString())
                        adapter = DataAdapter(this@Retrofit, data.data)
                        recyclerView2.adapter = adapter
                        recyclerView2.layoutManager = LinearLayoutManager(this@Retrofit)
                    }
                }

                override fun onFailure(call: Call<dataService>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
        else
        {
            val snack = Snackbar.make(layout1,"No Internet Connection",Snackbar.LENGTH_INDEFINITE)
            //snack.setBackgroundTint(Color.RED)
            snack.setAction("Retry", View.OnClickListener {
                getData()
            })
            snack.show()
        }
    }
}