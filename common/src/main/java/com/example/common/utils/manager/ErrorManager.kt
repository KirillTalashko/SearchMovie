package com.example.common.utils.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.common.extension.databaseErrorHandler
import com.example.common.extension.networkErrorHandler
import com.example.common.model.DialogInfo
import com.example.common.utils.ErrorOrigin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ErrorManager {

    private val errorMessageException = MutableSharedFlow<String>(replay = 3)

    private val _errorMessageDialog = MutableSharedFlow<DialogInfo>(replay = 3)
    val errorMessageDialog = _errorMessageDialog.asSharedFlow()

    private val _errorMessage = MutableSharedFlow<String>(replay = 3)
    val errorMessage = _errorMessage.asSharedFlow()

    private val _networkStatus = MutableLiveData<Boolean>()
    val networkStatus: LiveData<Boolean>
        get() = _networkStatus

    suspend fun postError(message: String) {
        _errorMessage.emit(message)
    }

    suspend fun showDialogGetLocalData(dialogInfo: DialogInfo) {
        _errorMessageDialog.emit(dialogInfo)
    }

    fun setNetworkChecker(status: Boolean) {
        _networkStatus.postValue(status)
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