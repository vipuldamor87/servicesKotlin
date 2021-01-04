package com.vipuldamor87.foregroundservice.Worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.vipuldamor87.foregroundservice.MainActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class RecurWorker(context: Context, params: WorkerParameters) : Worker(context,params) {

    override fun doWork(): Result {
        try {
            val workManager = WorkManager.getInstance(applicationContext)
            val uploadRequest = OneTimeWorkRequest.Builder(RecurWorker::class.java).setInitialDelay(1, TimeUnit.MINUTES).build()


            Log.d("date","working")

            workManager.enqueue(uploadRequest)
            return Result.success()
        } catch(e: Exception){
            return Result.failure()
        }
    }
}