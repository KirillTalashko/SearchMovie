package com.example.searchmovie

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout


class RatingCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : CardView(context,attrs,defStyleAttr) {
    private  var containerLayout: ConstraintLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.screen_rating_card, this, true)
        radius = 40f
        containerLayout = findViewById(R.id.container_layout_rating_card)
        containerLayout.setPadding(20,15,20,15)
    }
}
