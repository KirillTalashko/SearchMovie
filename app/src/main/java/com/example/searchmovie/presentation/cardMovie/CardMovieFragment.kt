package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.core.utils.ParametersCustomView
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.AdapterRelatedMovie
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import com.example.searchmovie.presentation.customView.InfoMovie

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
        interactionWithView()
    }

    private fun initRecyclerView() {
        binding.scrollSimilarMovie.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        binding.scrollSimilarMovie.adapter = adapter
        adapter.submitList(listOf("1", "2", "3", "4"))
    }

    private fun interactionWithView() {
        binding.imageMovieInCardMovie.loadPhoto(url)
        viewModel.trackingReadMore.observe(viewLifecycleOwner) {
            binding.infoMovie.setExpandableText(
                fullText = resources.getString(R.string.example_description_long),
                isExpanded = it,
                textColor = resources.getColor(R.color.black),
                isUnderline = it,
                onExpand = {viewModel.onReadMoreClicked()},
                onCollapse = { viewModel.onLessMoreClicked() }
            )
        }

        binding.infoMovie.setCharacteristics(
            quantity = InfoMovie.QuantityCustomView.TIME,
            settings = ParametersCustomView(
                drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.image_time) },
                firstText = getString(R.string.random_time),
                secondText = getString(R.string.minutes)
            )
        )

        binding.infoMovie.setCharacteristics(
            quantity = InfoMovie.QuantityCustomView.RATING,
            settings = ParametersCustomView(
                drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.image_star_gray) },
                firstText = getString(R.string.text_rating_movie),
                secondText = getString(R.string.text_rating)
            )
        )
        }
    }

