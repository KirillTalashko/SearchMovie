package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R

class NestedSimilarMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var imageView: ImageView
    private var textView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_similar_movie_second, this, true)
        imageView  = findViewById(R.id.image_similar_movie_second)
        textView = findViewById(R.id.text_similar_movie_second)
    }
    fun getImageSimilarMovie(): ImageView{
        return imageView
    }
    fun getTextSimilarMovie(): TextView{
        return textView
    }
}