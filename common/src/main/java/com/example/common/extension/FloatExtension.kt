package com.example.common.extension

import kotlin.math.floor

fun Float.reduceToDecimals(): Float {
    return floor(this * 10) / 10
}

fun Float.convectInJsonForResponse(): String {
    return this.toInt().toString().plus("-10")
}