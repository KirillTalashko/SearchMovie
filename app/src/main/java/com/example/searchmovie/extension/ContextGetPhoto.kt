package com.example.searchmovie.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.searchmovie.R

fun Context.getPhoto(url: String? = null , imageView: ImageView) {
    Glide.with(this)
        .load(url?: R.drawable.no_image)
        .placeholder(R.drawable.no_image)
        .into(imageView)
}


