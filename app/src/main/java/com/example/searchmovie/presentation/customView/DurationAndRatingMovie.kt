package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.databinding.CommonCustomViewBinding

class DurationAndRatingMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CommonCustomViewBinding? = null
    private val binding
        get() = _binding!!

    init {
        _binding = CommonCustomViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setImage(image: Int) {
        binding.imageViewTime.setImageResource(image)
    }

    fun setFirstText(text: String) {
        binding.textViewTimeInMinutes.text = text
    }

    fun setSecondText(text: String) {
        binding.textViewMinutes.text = text
    }
}