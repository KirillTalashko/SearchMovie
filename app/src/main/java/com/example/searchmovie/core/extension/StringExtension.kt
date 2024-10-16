package com.example.searchmovie.core.extension

import android.util.Log

fun String.log(){
    Log.i("youTag", this)
}
fun String.logActivity(){
    Log.i("activityTag", this)
}