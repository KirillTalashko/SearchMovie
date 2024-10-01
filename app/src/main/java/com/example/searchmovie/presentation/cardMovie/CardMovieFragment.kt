package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.utils.ImageHelper
import com.example.searchmovie.core.utils.ShowMoreOrLessText
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.AdapterRelatedMovie
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import javax.inject.Inject

class CardMovieFragment : Fragment() {

    private var _binding: FragmentCardMovieBinding? = null
    private val binding
        get() = _binding!!

    private val adapter = AdapterRelatedMovie()

    private val inject by lazy {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: ViewModelCardMovie by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this,
            factory
        )[ViewModelCardMovie::class.java]
    }

    private val url =
        "https://static.wikia.nocookie.net/d184dacd-7f49-43f8-a316-453d75ce8752/thumbnail-down/width/1280/height/720"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        binding.scrollSimilarMovie.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        binding.scrollSimilarMovie.adapter = adapter
        adapter.submitList(listOf("1", "2", "3", "4"))
    }

    private fun initViewModel() {
        ImageHelper().getPhoto(url, binding.imageMovieInCardMovie)
        binding.infoMovie.setTextString(resources.getString(R.string.example_description_long))
        setObserverMoreOrLessText(binding.infoMovie.getTextDescriptionMovie())
    }

    private fun setObserverMoreOrLessText(text: String) {
        object : ShowMoreOrLessText {
            override fun addReadMore() {
                if (text.length >= 250) {
                    val afterText = SpannableString(text.substring(0, 250).plus("...Read more"))
                    val clickableSpan: ClickableSpan = object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            addReadLess(text)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.isUnderlineText = false
                            TODO("как получить цвет с ресурсов?")
                        }
                    }
                    afterText.setSpan(
                        clickableSpan,
                        afterText.length - 10,
                        afterText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.infoMovie.setTextDescriptionMovie(afterText)
                    binding.infoMovie.setMovementMethodDescriptionMovie()
                } else {
                    binding.infoMovie.setTextDescriptionMovie(text)
                }
            }

            override fun addReadLess(text: String) {
                val beforeText = SpannableString(text.plus("...Read less"))
                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(view: View) {
                        addReadMore()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                    }
                }
                beforeText.setSpan(
                    clickableSpan,
                    beforeText.length - 10,
                    beforeText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.infoMovie.setTextDescriptionMovie(beforeText)
                binding.infoMovie.setMovementMethodDescriptionMovie()
            }
        }

    }
}
