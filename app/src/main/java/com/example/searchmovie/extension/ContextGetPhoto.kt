package com.example.searchmovie.extension

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

    fun Context.getPhoto(url: String, imageView: ImageView){
        Glide.with(this)
            .load(url)
            .into(imageView)
    }


