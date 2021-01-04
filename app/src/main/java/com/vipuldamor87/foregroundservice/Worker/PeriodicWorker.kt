package com.vipuldamor87.foregroundservice.Worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.*
import com.vipuldamor87.foregroundservice.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PeriodicWorker(context: Context, params: WorkerParameters) : Worker(context,params) {



    override fun doWork(): Result {
        try {
            val workManager = WorkManager.getInstance(applicationContext)
            val data: Data = Data.Builder()
                    .putInt(MainActivity.KEY_COUNT_VALUE,125)
                    .build()
            val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            val uploadRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                    .setConstraints(constraints)
                    .setInputData(data)
                    .build()
            workManager.enqueue(uploadRequest)
            return Result.success()

        } catch(e: Exception){
            return Result.failure()
        }
    }
}