package com.vipuldamor87.foregroundservice.ApiCalls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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

    private fun getData() {
        val data =Service.serviceInterface.getData()
        data.enqueue(object : retrofit2.Callback<dataService> {
            override fun onResponse(call: Call<dataService>, response: Response<dataService>) {

                val data = response.body()
                if(data!= null)
                {
                     Log.d("mytag",data.toString())
                    adapter= DataAdapter(this@Retrofit,data.data)
                    recyclerView2.adapter = adapter
                    recyclerView2.layoutManager = LinearLayoutManager(this@Retrofit)
                }
            }

            override fun onFailure(call: Call<dataService>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}