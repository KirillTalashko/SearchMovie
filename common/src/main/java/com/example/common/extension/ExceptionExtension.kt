package com.example.common.extension

import android.database.sqlite.SQLiteException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Exception.networkErrorHandler(): String? {
    return when (this) {
        is NullPointerException -> {
            this.localizedMessage
        }

        is SocketTimeoutException -> {
            this.localizedMessage
        }

        is UnknownHostException -> {
            this.localizedMessage
        }

        is IOException -> {
            this.localizedMessage
        }

        is IllegalArgumentException -> {
            this.localizedMessage
        }

        is IllegalStateException -> {
            this.localizedMessage
        }

        else -> "The error is not network related + ${this.localizedMessage}"
    }
}

fun Exception.databaseErrorHandler(): String? {
    return when (this) {
        is SQLiteException -> {
            this.localizedMessage
        }

        is IllegalStateException -> {
            this.localizedMessage
        }

        is RuntimeException -> {
            this.localizedMessage
        }

        else -> "The error is not database related + ${this.localizedMessage}"
    }
}

suspend fun Exception.isCheckErrorNetwork(action: suspend () -> Unit) {
    return when (this) {
        is NullPointerException -> {
            action.invoke()
        }

        is SocketTimeoutException -> {
            action.invoke()
        }

        is UnknownHostException -> {
            action.invoke()
        }

        is IOException -> {
            action.invoke()
        }

        is IllegalArgumentException -> {
            action.invoke()
        }

        is IllegalStateException -> {
            action.invoke()
        }

        else -> Unit
    }
}