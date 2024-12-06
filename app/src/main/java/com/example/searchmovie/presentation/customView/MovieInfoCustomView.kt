package com.example.searchmovie.presentation.customView

import android.content.Context
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.common.model.ValueHolderView
import com.example.common.utils.`object`.TextExpander
import com.example.network.modelsMovie.Genres
import com.example.searchmovie.R
import com.example.searchmovie.databinding.ScreenInformationMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.MovieGenresAdapter

class MovieInfoCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: ScreenInformationMovieBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: MovieGenresAdapter by lazy(LazyThreadSafetyMode.NONE) { MovieGenresAdapter() }

    init {
        _binding = ScreenInformationMovieBinding.inflate(LayoutInflater.from(context), this)
        binding.rvListGenre.adapter = adapter
    }

    fun setDataInfoMovie(name: String, year: String, genres: List<Genres>) {
        binding.apply {
            textViewNameInInfoMovie.text = name
            textViewYearMovie.text = year
            adapter.submitList(genres)
        }
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

    fun setDataCustomView(display: DisplayOptionsCustomView, owner: ValueHolderView) {
        when (display) {
            DisplayOptionsCustomView.TIME -> {
                binding.customViewDurationMovie.setData(
                    firstText = owner.firstText,
                    secondText = owner.secondText, drawable = owner.drawable,
                    visible = true
                )
            }

            DisplayOptionsCustomView.RATING_IMDB -> {
                binding.customViewRatingImdbMovie.setData(
                    firstText = owner.firstText,
                    secondText = owner.secondText,
                    drawable = owner.drawable,
                    visible = owner.firstText?.toFloat() != 0F
                )
            }

            DisplayOptionsCustomView.RATING_KP -> {
                binding.customViewRatingKpMovie.setData(
                    firstText = owner.firstText,
                    secondText = owner.secondText,
                    drawable = owner.drawable,
                    visible = owner.firstText?.toFloat() != 0F
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
        RATING_IMDB,
        RATING_KP
    }

}