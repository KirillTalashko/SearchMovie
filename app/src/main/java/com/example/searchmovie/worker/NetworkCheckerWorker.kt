package com.example.searchmovie.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.common.utils.Const
import com.example.common.utils.manager.ErrorManager
import com.example.common.utils.manager.NetworkManager
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import kotlinx.coroutines.delay
import javax.inject.Inject


class NetworkCheckerWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {


    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var errorManager: ErrorManager

    private var work = true
    private var previousNetworkIsConnect: Boolean? = null

    init {
        (context.applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        while (work) {

            val interval = IntervalTimer.setIntervalTime(IntervalTimer.MIDDLE_TIME)
            delay(interval)
            val networkIsConnect = networkManager.isConnect()
            Const.isChecked = networkIsConnect
            errorManager.setNetworkChecker(networkIsConnect)

            if (previousNetworkIsConnect != networkIsConnect)
                if (!networkIsConnect) {
                    errorManager.postError(context.getString(R.string.no_internet))
                    IntervalTimer.counterReset()
                } else {
                    errorManager.postError(context.getString(R.string.connect_internet))
                }

            previousNetworkIsConnect = networkIsConnect
        }
        return Result.success()
    }

    fun stopWork() {
        work = false
    }

}