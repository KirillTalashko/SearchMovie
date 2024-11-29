package com.example.searchmovie.core.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.common.extension.log
import com.example.common.utils.Core
import com.example.common.utils.IntervalTimer
import com.example.searchmovie.SearchMovieApp
import kotlinx.coroutines.delay
import javax.inject.Inject


class NetworkCheckerWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {


    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var core: Core

    init {
        (context.applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    private val work = true

    override suspend fun doWork(): Result {
        while (work) {
            delay(IntervalTimer.setIntervalTime(3000L))
            Core.isConnected = networkManager.isConnect()
            Core.isChecked = !networkManager.isConnect()
            "isConnected ${Core.isConnected}, isChecked ${Core.isChecked}".log()
            core._networkChecker.emit(networkManager.isConnect())
        }
        return Result.success()
    }

}