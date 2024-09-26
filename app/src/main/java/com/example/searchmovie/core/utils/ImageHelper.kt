package com.example.searchmovie.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.searchmovie.R

class ImageHelper {
    fun getPhoto(url: String? = null, imageView: ImageView) {
        Glide.with(imageView.rootView)
            .load(url)
            .placeholder(R.drawable.no_image)
            .error(R.drawable.no_image)
            .into(imageView)
    }
}