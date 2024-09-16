package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.example.searchmovie.R

class DescriptionReadMore @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var textDirection: TextView

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.text_description_read_more, FrameLayout(context), true)
        textDirection = findViewById(R.id.read_more)
    }
    fun getTextView():TextView{
        return textDirection
    }
}