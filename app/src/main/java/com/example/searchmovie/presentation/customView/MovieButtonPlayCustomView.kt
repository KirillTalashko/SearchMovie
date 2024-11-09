package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R

class MovieButtonPlayCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private  var textTrailerView: TextView
    private var textNameView: TextView
    private var containerLayout: ConstraintLayout
    private var imagePlayCard : ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_play_card, this, true)
        radius = 44f
        textTrailerView = findViewById(R.id.text_view_title_watch_movie)
        textNameView = findViewById(R.id.text_view_name_in_card_play_movie)
        containerLayout = findViewById(R.id.container_layout_card)
        imagePlayCard = findViewById(R.id.image_view_button_play_movie)
        containerLayout.setPadding(30, 30, 30, 30)
        marginChildView()
    }

    private fun marginChildView() {
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
    fun getMovieNameTextView(): TextView {
         return textNameView
     }

}