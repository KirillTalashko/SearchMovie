package com.example.searchmovie.extension

import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Exception.checkingResponse(): String{
        when(this) {
            is NullPointerException ->{
                return "Список пуст"
            }
            is SocketTimeoutException -> {
                return "Истекло время ожидания от сервера"
            }
            is UnknownHostException -> {
                return "Нет соединения с сервером"
            }
        }
        return this.message!!
    }