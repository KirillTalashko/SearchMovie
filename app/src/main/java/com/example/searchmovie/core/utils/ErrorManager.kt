package com.example.searchmovie.core.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object ErrorManager {

    private val _errorMassage = MutableSharedFlow<String>(replay = 3)

    val errorMassage: SharedFlow<String>
        get() = _errorMassage

    suspend fun postError(massage: String) {
        _errorMassage.emit(massage)
    }

}