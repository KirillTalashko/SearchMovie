package com.example.searchmovie.presentation.customView

import android.content.Context
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R

class InfoMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val textDescriptionMovie: TextView
    init {
        LayoutInflater.from(context).inflate(R.layout.information_movie, this, true)
        textDescriptionMovie = findViewById(R.id.text_description_movie)
    }
    fun getTextDescriptionMovie() = textDescriptionMovie.text.toString()
    fun setTextDescriptionMovie(text: SpannableString){
        textDescriptionMovie.text = text
    }
    fun setTextDescriptionMovie(text: String){
        textDescriptionMovie.text = text
    }
    fun setTextString(text: String){
        textDescriptionMovie.text = text
    }
    fun setMovementMethodDescriptionMovie(){
        textDescriptionMovie.movementMethod = LinkMovementMethod.getInstance()
    }
}