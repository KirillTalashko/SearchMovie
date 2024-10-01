package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R

class DateAndGenreMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    init{
    LayoutInflater.from(context).inflate(R.layout.screen_date_and_genre,this,true)
    }

    /*private fun setIndents() {
        var params = binding.releaseDate.layoutParams as? MarginLayoutParams
        params?.let {
            it.setMargins(30, 10, 10, 10)
            layoutParams = it
        }
        params = binding.genreMovie.layoutParams as? MarginLayoutParams
        params?.let {
            it.marginStart = 50
            layoutParams = it
        }
        params = binding.yearMovie.layoutParams as? MarginLayoutParams
        params?.let {
            it.setMargins(30, 15, 40, 15)
            layoutParams = it
        }
        params = binding.lastButton.layoutParams as? MarginLayoutParams
        params?.let {
            it.marginStart = 25
            layoutParams = it
        }
        params = binding.firstButton.layoutParams as? MarginLayoutParams
        params?.let {
            it.marginStart = 25
            layoutParams = it
        }
    }*/
}