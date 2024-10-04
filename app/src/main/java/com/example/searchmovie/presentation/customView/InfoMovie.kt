package com.example.searchmovie.presentation.customView

import android.content.Context
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.searchmovie.R
import com.example.searchmovie.core.utils.ParametersCustomView
import com.example.searchmovie.core.utils.TextExpander
import com.example.searchmovie.databinding.InformationMovieBinding
import com.example.searchmovie.di.modules.CommonModule

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
                    resources.getString(R.string.read_less_text)
                )
            } else {
                TextExpander.getReadMoreText(
                    fullText,
                    textColor,
                    isUnderline,
                    onExpand,
                    resources.getString(R.string.read_more_text)
                )
            }
        )
    }

    fun setCharacteristics(quantity: QuantityCustomView, settings: ParametersCustomView) {
        when (quantity) {
            QuantityCustomView.TIME -> {
                binding.customViewTimes.setData(
                    firstText = settings.firstText,
                    secondText = settings.secondText,
                    drawable = settings.drawable
                )
            }

            QuantityCustomView.RATING -> {
                binding.customViewRating.setData(
                    firstText = settings.firstText,
                    secondText = settings.secondText,
                    drawable = settings.drawable
                )
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    enum class QuantityCustomView() {
        TIME,
        RATING
    }

}