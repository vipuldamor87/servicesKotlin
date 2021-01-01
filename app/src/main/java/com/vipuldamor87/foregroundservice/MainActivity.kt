package com.vipuldamor87.foregroundservice

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.work.*
import com.vipuldamor87.foregroundservice.Services.MyForeService
import com.vipuldamor87.foregroundservice.Services.MyLocationForeService
import com.vipuldamor87.foregroundservice.Worker.PeriodicWorker
import com.vipuldamor87.foregroundservice.Worker.UploadWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object{
        const val KEY_COUNT_VALUE = "key_count"
    }

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
                startForegroundService(Intent(this, MyForeService::class.java))
            }
            else
            {
                startService(Intent(this, MyForeService::class.java))
            }
        }
        startGettingLocation.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                Log.d("Oreo","This works")
                startForegroundService(Intent(this, MyLocationForeService::class.java))
            }
            else
            {
                startService(Intent(this, MyLocationForeService::class.java))
            }
        }

        //WorkerClass
        uploadWorker.setOnClickListener {
            setOneTimeWorkRequest()
        }
        periodicWorker.setOnClickListener{
            setPeriodicWorkRequest()
        }

    }
    private fun setOneTimeWorkRequest(){
        val workManager = WorkManager.getInstance(applicationContext)
        val data: Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE,125)
            .build()
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()
        workManager.enqueue(uploadRequest)
        workManager.getWorkInfoByIdLiveData(uploadRequest.id)
            .observe(this, Observer {
                tv1.text =it.state.name
                if(it.state.isFinished){
                    val data = it.outputData
                    val message = data.getString(UploadWorker.KEY_WORKER)
                        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun setPeriodicWorkRequest(){
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(PeriodicWorker::class.java,15,TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)

    }
}