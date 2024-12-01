package com.example.searchmovie.presentation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.searchmovie.R
import com.example.searchmovie.databinding.ErrorAnimationCustomViewBinding

class ErrorCustomView(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding: ErrorAnimationCustomViewBinding = ErrorAnimationCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        visibility = GONE
        translationY = -resources.getDimensionPixelSize(R.dimen.error_view_height).toFloat()
        alpha = 0f
    }

    fun showError(errorMassage: String) {
        visibility = VISIBLE
        binding.textViewError.text = errorMassage

        animate()
            .translationY(0f)
            .translationX(5f)
            .alpha(1f)
            .setDuration(2000)
            .start()

        postDelayed({ hideError() }, 3000)
    }

    private fun hideError() {
        animate()
            .translationY(-resources.getDimensionPixelSize(R.dimen.error_view_height).toFloat())
            .alpha(0.8f)
            .setDuration(2000)
            .withEndAction {
                visibility = GONE
            }
            .start()
    }
}