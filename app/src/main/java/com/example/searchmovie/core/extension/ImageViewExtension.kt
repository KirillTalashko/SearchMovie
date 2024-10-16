package com.example.searchmovie.core.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

import com.example.searchmovie.R


fun ImageView.loadPhoto(
    url: String,
    placeholder: Int = R.drawable.is_loading_white,
    error: Int = R.drawable.no_image
) {
    Glide.with(this.rootView)
        .load(url)
        .placeholder(placeholder)
        .error(error)
        .into(this)
}