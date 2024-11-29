package com.example.common.utils

import com.example.common.extension.log

object IntervalTimer {

    private var intervalTime = 0L
    private const val MAX_TIME = 1800000L

    fun setIntervalTime(interval: Long): Long {
        intervalTime += interval
        if (intervalTime == MAX_TIME) {
            intervalTime = interval
        }
        "intervalTime $intervalTime".log()
        return intervalTime
    }

}