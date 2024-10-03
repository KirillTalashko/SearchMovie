package com.example.searchmovie.presentation.customView

import android.content.Context
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.searchmovie.R
import com.example.searchmovie.core.utils.TextExpander
import com.example.searchmovie.databinding.InformationMovieBinding

class InfoMovie @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: InformationMovieBinding? = null
    private val binding
        get() = _binding!!

    init {
        _binding = InformationMovieBinding.inflate(LayoutInflater.from(context), this)
    }

    private fun setCustomText(text: SpannableString) {
        binding.textDescriptionMovie.text = text
        binding.textDescriptionMovie.movementMethod = LinkMovementMethod.getInstance()
    }

    fun setExpandableText(
        fullText: String,
        isExpanded: Boolean,
        textColor: Int = resources.getColor(R.color.black),
        isUnderline: Boolean = false,
        onExpand: () -> Unit,
        onCollapse: () -> Unit
    ) {
        val text = if (isExpanded) {
            resources.getString(R.string.read_more_text)
        } else {
            resources.getString(R.string.read_less_text)
        }
        setCustomText(
            if (isExpanded){
                TextExpander.getReadLessText(fullText, textColor, isUnderline, onCollapse, text)
            } else {
                TextExpander.getReadMoreText(fullText, textColor, isUnderline, onExpand, text)
            }
        )
    }

    fun setTime(image: Int, firstText: String, secondText: String){
        binding.durationAndRating.customViewTimes.setImage(image)
        binding.durationAndRating.customViewTimes.setFirstText(firstText)
        binding.durationAndRating.customViewTimes.setSecondText(secondText)
    }
    fun setRating(image: Int, firstText: String, secondText: String){
        binding.durationAndRating.customViewRating.setImage(image)
        binding.durationAndRating.customViewRating.setFirstText(firstText)
        binding.durationAndRating.customViewRating.setSecondText(secondText)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}