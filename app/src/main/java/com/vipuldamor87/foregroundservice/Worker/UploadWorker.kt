package com.vipuldamor87.foregroundservice.Worker

import android.content.Context
import android.util.Log
import androidx.work.*
import com.vipuldamor87.foregroundservice.MainActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class UploadWorker(context:Context, params:WorkerParameters) : Worker(context,params) {

    companion object{
        const val KEY_WORKER = "key_worker"
    }

    override fun doWork(): Result {
        try {
            val workManager = WorkManager.getInstance(applicationContext)
            val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java).setInitialDelay(1,TimeUnit.MINUTES).build()

            val count = inputData.getInt(MainActivity.KEY_COUNT_VALUE,0)
            for (i in 0 until count) {
                Log.d("Worker", "uploading")
            }

            val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = time.format(Date())

            val outPutData = Data.Builder()
                .putString(KEY_WORKER,currentDate)
                .build()

            workManager.enqueue(uploadRequest)
            return Result.success(outPutData)
        } catch(e:Exception){
            return Result.failure()
        }
    }
}