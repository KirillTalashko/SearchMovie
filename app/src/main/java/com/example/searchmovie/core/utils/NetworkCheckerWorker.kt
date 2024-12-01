package com.example.searchmovie.core.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.common.utils.Core
import com.example.common.utils.IntervalTimer
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

    init {
        (context.applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    private var work = true
    private var previousNetworkAccess: Boolean? = null

    override suspend fun doWork(): Result {
        while (work) {
            val interval = IntervalTimer.setIntervalTime(IntervalTimer.MIDDLE_TIME)

            delay(interval)

            val networkAccess = networkManager.isInternetReachable(IntervalTimer.MIDDLE_TIME)

            Core.isChecked = networkAccess

            errorManager.setNetworkChecker(networkAccess)

            if (previousNetworkAccess != networkAccess)
                if (!networkAccess) {
                    errorManager.postError(context.getString(R.string.no_internet))
                } else {
                    errorManager.postError(context.getString(R.string.connect_internet))
                }

            previousNetworkAccess = networkAccess
        }
        return Result.success()
    }

    fun stopWork() {
        work = false
    }

}