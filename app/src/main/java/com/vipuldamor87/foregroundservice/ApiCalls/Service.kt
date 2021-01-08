package com.vipuldamor87.foregroundservice.ApiCalls

import retrofit2.Call
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

//https://reqres.in/api/users?page=2
val BASE_URL ="https://reqres.in/api/"
interface ServiceInterface {
    @GET("users?page=2")
    fun getData(): Call<dataService>
}
object Service{
    val serviceInterface: ServiceInterface
    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        serviceInterface = retrofit.create(ServiceInterface::class.java)
    }

}