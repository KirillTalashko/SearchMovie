package com.example.searchmovie.presentation.customView

import android.content.Context
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.searchmovie.R
import com.example.searchmovie.core.utils.ValueHolderView
import com.example.searchmovie.core.utils.TextExpander
import com.example.searchmovie.databinding.ScreenInformationMovieBinding

class InfoMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: ScreenInformationMovieBinding? = null
    private val binding
        get() = _binding!!

    init {
        _binding = ScreenInformationMovieBinding.inflate(LayoutInflater.from(context), this)
    }

    private fun setCustomText(text: SpannableString) {
        binding.textViewDescriptionMovie.text = text
        binding.textViewDescriptionMovie.movementMethod = LinkMovementMethod.getInstance()
    }

    fun setExpandableText(
        fullText: String,
        isExpanded: Boolean,
        textColor: Int = ContextCompat.getColor(context,R.color.black),
        isUnderline: Boolean = false,
        onExpand: () -> Unit,
        onCollapse: () -> Unit
    ) {
        setCustomText(
            if (isExpanded) {
                TextExpander.getReadLessText(
                    fullText,
                    textColor,
                    isUnderline,
                    onCollapse,
                    ContextCompat.getString(context,R.string.read_less_text)
                )
            } else {
                TextExpander.getReadMoreText(
                    fullText,
                    textColor,
                    isUnderline,
                    onExpand,
                    ContextCompat.getString(context,R.string.read_more_text)
                )
            }
        )
    }

    fun setCharacteristics(display: DisplayOptionsCustomView, owner: ValueHolderView) {
        when (display) {
            DisplayOptionsCustomView.TIME -> {
                binding.customViewDurationMovie.setData(

                    firstText = owner.firstText,
                    secondText = owner.secondText,
                    drawable = owner.drawable
                )
            }

            DisplayOptionsCustomView.RATING -> {
                binding.customViewRatingMovie.setData(
                    firstText = owner.firstText,
                    secondText = owner.secondText,
                    drawable = owner.drawable
                )
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    enum class DisplayOptionsCustomView {
        TIME,
        RATING
    }

}