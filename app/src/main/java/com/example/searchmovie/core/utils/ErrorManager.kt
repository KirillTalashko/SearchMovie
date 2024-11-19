package com.example.searchmovie.core.utils

import android.content.Context
import com.example.common.extension.databaseErrorHandler
import com.example.common.extension.networkErrorHandler
import com.example.common.model.DialogParameterMode
import com.example.common.utils.ErrorOrigin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class ErrorManager(private val context: Context) {

    private val errorMessageException = MutableSharedFlow<String>(replay = 3)

    private val _errorMessageDialog = MutableStateFlow(DialogParameterMode(isEnabled = false))
    val errorMessageDialog = _errorMessageDialog.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>(replay = 3)
    val errorMessage = _errorMessage.asSharedFlow()


    suspend fun postError(message: String) {
        _errorMessage.emit(message)
    }

    suspend fun showDialogGetLocalData(dialogParameterMode: DialogParameterMode) {
        _errorMessageDialog.emit(dialogParameterMode)
    }

    suspend fun postError(exception: Exception, dataErrorOrigin: ErrorOrigin) {
        when (dataErrorOrigin) {
            ErrorOrigin.NETWORK -> {
                exception.networkErrorHandler()?.let { errorMessageException.emit(it) }
            }

            ErrorOrigin.DATABASE -> {
                exception.databaseErrorHandler()?.let { errorMessageException.emit(it) }
            }
        }
        // This method sends data to analyse the errors received
    }
}