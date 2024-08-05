package com.example.searchmovie

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView

class PlayCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(context,attrs,defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_play_card, this, true)
    }
}