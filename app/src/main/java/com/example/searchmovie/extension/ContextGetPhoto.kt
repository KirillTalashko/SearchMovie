package com.example.searchmovie.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.searchmovie.R

fun Context.getPhoto(url: String, imageView: ImageView) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.no_image)
        .into(imageView)
}


