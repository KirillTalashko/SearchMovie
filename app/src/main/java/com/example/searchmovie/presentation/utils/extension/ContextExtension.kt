package com.example.searchmovie.presentation.utils.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
}





