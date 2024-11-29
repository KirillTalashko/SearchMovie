package com.example.common.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Core {
    companion object {
        @Volatile
        var isConnected = true

        @Volatile
        var isChecked = false
    }

    var _networkChecker: MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 1)

    val networkChecker = _networkChecker.asSharedFlow()
}