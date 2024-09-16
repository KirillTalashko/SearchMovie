package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.databinding.ScreenDurationAndRatingMovieBinding

class DurationAndRatingMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: ScreenDurationAndRatingMovieBinding? = null
    private val binding
        get() = _binding!!

    init {
        _binding = ScreenDurationAndRatingMovieBinding.inflate(LayoutInflater.from(context), this, true)
        setIndents()
        binding.timeInMinutes.textSize = 15f
        binding.textRatingCardMovie.textSize = 15f

    }

    private fun setIndents() {
        var params = binding.timeInMinutes.layoutParams as? MarginLayoutParams
        params?.let {
            it.setMargins(20, 0, 10, 0)
            layoutParams = it
        }
        params = binding.imageStarGray.layoutParams as? MarginLayoutParams
        params?.let {
            it.marginStart = 30
            layoutParams = it
        }
        params = binding.textRatingCardMovie.layoutParams as? MarginLayoutParams
        params?.let {
            it.setMargins(20, 0, 10, 0)
            layoutParams = it
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}