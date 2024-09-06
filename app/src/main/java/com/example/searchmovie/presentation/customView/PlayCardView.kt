package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R

class PlayCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private lateinit var textTrailerView: TextView
    private var textNameView: TextView
    private var containerLayout: ConstraintLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_play_card, this, true)
        radius = 44f
        marginChildView()
        textNameView = findViewById(R.id.text_name_movie)
        containerLayout = findViewById(R.id.container_layout_card)
        containerLayout.setPadding(30, 30, 30, 30)
    }

    private fun marginChildView() {
        textTrailerView = findViewById(R.id.text_watch_movie)
        textNameView = findViewById(R.id.text_name_movie)
        var params = textTrailerView.layoutParams as? MarginLayoutParams
        params?.let {
            it.marginStart = 30
            layoutParams = it
        }
        params = textNameView.layoutParams as? MarginLayoutParams
        params?.let {
            it.marginStart = 30
            layoutParams = it
        }
    }
     fun getTextNameView():TextView {
         return textNameView
     }

}