package com.example.common.utils

object IntervalTimer {

    private const val TIME = 1

    fun setIntervalTime(interval: Int): Long {
        return (TIME + interval).toLong()
    }

}