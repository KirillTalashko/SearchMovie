package com.example.common.utils.`object`

object IntervalTimer {

    private var intervalTime = 0L
    private const val MAX_TIME = 180000L

    const val LONG_TIME = 5000L
    const val FAST_TIME = 1000L
    const val MIDDLE_TIME = 3000L

    fun setIntervalTime(interval: Long): Long {
        intervalTime += interval
        if (intervalTime == MAX_TIME) {
            intervalTime = interval
        }
        return intervalTime
    }

    fun counterReset() {
        intervalTime = 0L
    }

}