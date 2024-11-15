package com.example.searchmovie.core.utils

import android.content.Context
import com.example.searchmovie.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ErrorManager(private val context: Context) {

    val dataObserver = MutableSharedFlow<DataErrorOrigin>(replay = 3)

    private val _errorMassage = MutableSharedFlow<String>(replay = 3)

    val errorMassage: SharedFlow<String>
        get() = _errorMassage

    private suspend fun postError(massage: String) {
        _errorMassage.emit(massage)
    }

    init {
        suspend {
            postLocalData()
        }
    }

    private suspend fun postLocalData() {
        dataObserver.collect { type ->
            when (type) {
                DataErrorOrigin.NETWORK -> {
                    postError(context.getString(R.string.network_date))
                }

                DataErrorOrigin.DATABASE -> {
                    postError(context.getString(R.string.local_date))
                }
            }
        }
    }


    enum class DataErrorOrigin {
        NETWORK,
        DATABASE
    }

}