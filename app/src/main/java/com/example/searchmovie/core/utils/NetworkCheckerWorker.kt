package com.example.searchmovie.core.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.common.utils.Core
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NetworkCheckerWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private var jobNetworkChecker: Job? = null

    private val networkManager: NetworkManager by lazy(LazyThreadSafetyMode.NONE) {
        NetworkManager(
            context
        )
    }

    override suspend fun doWork(): Result {
        try {
            jobNetworkChecker = CoroutineScope(SupervisorJob()).launch(Dispatchers.Unconfined) {
                Core.isConnected = networkManager.isConnect()
            }
        } catch (workerException: Exception) {
            jobNetworkChecker?.cancel()
            return Result.failure()
        }
        return Result.success()
    }


}