package com.example.searchmovie

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class PlayCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private lateinit var imageView: ImageView
    private lateinit var textTrailerView: TextView
    private lateinit var textNameView: TextView
    private var containerLayout : ConstraintLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_play_card, this, true)
        radius = 44f
        marginChildView()
        containerLayout = findViewById(R.id.container_layout_card)
        containerLayout.setPadding(0,0,30,0)
    }

    private fun marginChildView() {
        imageView = findViewById(R.id.image_play_bottom)
        textTrailerView = findViewById(R.id.text_watch_movie)
        textNameView = findViewById(R.id.text_name_movie)
        var params = imageView.layoutParams as? MarginLayoutParams
        params?.let {
            it.setMargins(30, 30, 0, 30)
            layoutParams = it
        }
        params = textTrailerView.layoutParams as? MarginLayoutParams
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
}