package com.example.common.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
}





