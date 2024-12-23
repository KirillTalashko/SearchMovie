package com.example.searchmovie.presentation.customView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R
import com.example.searchmovie.databinding.CommonCustomViewBinding

class CommonCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CommonCustomViewBinding? = null
    private val binding
        get() = _binding!!

    init {
        _binding = CommonCustomViewBinding.inflate(LayoutInflater.from(context), this,true)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CommonCustomView)
        val firstText = attributes.getString(R.styleable.CommonCustomView_first_text)
        val secondText = attributes.getString(R.styleable.CommonCustomView_second_text)
        val drawableRes = attributes.getDrawable(R.styleable.CommonCustomView_drawable_res)
        val visible = attributes.getBoolean(R.styleable.CommonCustomView_visible, true)
        attributes.recycle()
        setData(firstText, secondText, drawableRes, visible)
    }

    fun setData(firstText: String?, secondText: String?, drawable: Drawable?, visible: Boolean) {
        binding.apply {
            imageViewSmallImage.setImageDrawable(drawable)
            textViewFirstText.text = firstText
            textViewSecondText.text = secondText
            if (!visible) {
                binding.root.visibility = GONE
            }
        }
    }

}