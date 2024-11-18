package com.example.searchmovie.core.utils

import android.content.Context
import com.example.common.extension.databaseErrorHandler
import com.example.common.extension.networkErrorHandler
import com.example.common.utils.DataErrorOrigin
import com.example.searchmovie.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class ErrorManager(private val context: Context) {

    private val errorMessageExpectation = MutableSharedFlow<String>(replay = 3)

    private val _errorMessageDialog = MutableStateFlow(false)
    val errorMessageDialog = _errorMessageDialog.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>(replay = 3)
    val errorMessage = _errorMessage.asSharedFlow()


    suspend fun postError(dataErrorOrigin: DataErrorOrigin) {
        when (dataErrorOrigin) {
            DataErrorOrigin.NETWORK -> {
                _errorMessage.emit(context.getString(R.string.network_date))
            }

            DataErrorOrigin.DATABASE -> {
                _errorMessage.emit(context.getString(R.string.local_date))
            }
        }
    }

    suspend fun postError(exception: Exception, dataErrorOrigin: DataErrorOrigin) {
        when (dataErrorOrigin) {
            DataErrorOrigin.NETWORK -> {
                exception.networkErrorHandler()?.let { errorMessageExpectation.emit(it) }
            }

            DataErrorOrigin.DATABASE -> {
                exception.databaseErrorHandler()?.let { errorMessageExpectation.emit(it) }
            }
        }
        // This method sends data to analyse the errors received
    }
}