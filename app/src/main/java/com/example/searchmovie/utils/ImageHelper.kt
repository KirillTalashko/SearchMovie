package com.example.searchmovie.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.searchmovie.R

class ImageHelper {
    fun getPhoto(url: String? = null, imageView: ImageView) {
        Glide.with(imageView.rootView)
            .load(url ?: R.drawable.no_image)
            .placeholder(R.drawable.no_image)
            .into(imageView)
    }
}