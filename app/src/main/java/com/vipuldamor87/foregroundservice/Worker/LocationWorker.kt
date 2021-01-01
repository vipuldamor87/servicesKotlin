package com.vipuldamor87.foregroundservice.Worker

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class LocationWorker(context: Context, params: WorkerParameters) : Worker(context,params) {
    lateinit var lm : LocationManager
    lateinit var loc: Location
    private val CHANNEL_ID = "LocationWithKotlin"

    override fun doWork(): Result {
        try {
            //val count = inputData.getInt(MainActivity.KEY_COUNT_VALUE,0)
            for (i in 0..30) {
                Log.d("Worker", "uploading")
            }
            val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                    .build()
            val parallelWorker = mutableListOf<OneTimeWorkRequest>()
            parallelWorker.add(uploadRequest)
            WorkManager.getInstance(applicationContext)
                    .beginWith(parallelWorker)
                    .enqueue()


            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())
            Log.d("PeriodicWorker","$currentDate")

            return Result.success()
        } catch(e: Exception){
            return Result.failure()
        }
    }
}