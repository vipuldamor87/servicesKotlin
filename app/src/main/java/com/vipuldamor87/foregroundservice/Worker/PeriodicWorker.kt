package com.vipuldamor87.foregroundservice.Worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.vipuldamor87.foregroundservice.MainActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PeriodicWorker(context: Context, params: WorkerParameters) : Worker(context,params) {



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