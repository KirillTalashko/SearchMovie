package com.example.common.utils

object Const {
    const val LIMIT = 10

    var firstLaunch = true

    @Volatile
    var isChecked = true

    var position: Int = 0
}