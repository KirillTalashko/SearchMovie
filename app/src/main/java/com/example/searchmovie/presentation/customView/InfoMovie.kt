package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.databinding.InformationMovieBinding

class InfoMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: InformationMovieBinding? = null
    private val binding
        get() = _binding!!

    init {
        _binding = InformationMovieBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun getReadMore(): TextView {
        return binding.descriptionMovie
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}