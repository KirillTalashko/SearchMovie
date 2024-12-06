package com.example.common.utils.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.common.R
import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.common.utils.`object`.Core
import com.example.common.utils.`object`.IntervalTimer
import kotlinx.coroutines.delay
import javax.inject.Inject


class NetworkCheckerWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {


    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var errorManager: ErrorManager

    private val networkRequest: OneTimeWorkRequest =
        OneTimeWorkRequestBuilder<NetworkCheckerWorker>().build()

    private var work = true
    private var previousNetworkIsConnect: Boolean? = null

    init {
        WorkManager.getInstance(context)
            .enqueueUniqueWork("NetworkChecker", ExistingWorkPolicy.REPLACE, networkRequest)

        (context.applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        while (work) {
            val interval = IntervalTimer.setIntervalTime(IntervalTimer.MIDDLE_TIME)
            delay(interval)
            val networkIsConnect = networkManager.isConnect()
            Core.isChecked = networkIsConnect
            errorManager.setNetworkChecker(networkIsConnect)

            if (previousNetworkIsConnect != networkIsConnect)
                if (!networkIsConnect) {
                    errorManager.postError(context.getString(R.string.no_internet_common))
                    IntervalTimer.counterReset()
                } else {
                    errorManager.postError(context.getString(R.string.connect_internet_common))
                }

            previousNetworkIsConnect = networkIsConnect
        }
        return Result.success()
    }

    fun stopWork() {
        WorkManager.getInstance(context).cancelUniqueWork("NetworkChecker")
    }

}