package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.databinding.ScreenDateAndGenreBinding

class DateAndGenreMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: ScreenDateAndGenreBinding? = null

    private val binding
        get() = _binding!!

    init {
        _binding = ScreenDateAndGenreBinding.inflate(LayoutInflater.from(context), this, true)
        setIndents()
    }

    private fun setIndents() {
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
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}