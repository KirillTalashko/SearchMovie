package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.utils.BaseFragment
import com.example.common.utils.ValueHolderView
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.core.extension.log
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.AdapterRelatedMovie
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import com.example.searchmovie.presentation.customView.InfoMovie
import javax.inject.Inject

class CardMovieFragment :
    BaseFragment<FragmentCardMovieBinding>(FragmentCardMovieBinding::inflate) {

    private val argsMovie: CardMovieFragmentArgs by navArgs()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "${argsMovie.infoMovie.rating.imd}".log()
        "${argsMovie.infoMovie.duration}".log()
        "${argsMovie.infoMovie.genres?.size}".log()
        initRecyclerView()
        interactionWithView()
    }

    private fun initRecyclerView() {
        binding.rvScrollSimilarMovie.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        binding.rvScrollSimilarMovie.adapter = adapter
        adapter.submitList(listOf("1", "2", "3", "4"))
    }

    private fun interactionWithView() {
        binding.apply {
            imageViewPosterMovie.loadPhoto(argsMovie.infoMovie.poster?.url ?: "")

            customViewInfoMovie.setDataInfoMovie(
                name = argsMovie.infoMovie.name ?: getString(R.string.no_name),
                year = argsMovie.infoMovie.year.toString(),
                genres = argsMovie.infoMovie.genres ?: emptyList()
            )

            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.TIME,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(requireContext(), R.drawable.image_time),
                    firstText = argsMovie.infoMovie.duration.toString() ,
                    secondText = getString(R.string.minutes)
                )
            )

            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.RATING,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_star_gray
                    ),
                    firstText = argsMovie.infoMovie.rating.imd.toString(),
                    secondText = getString(R.string.text_rating)
                )
            )

        }

        textExpander()
    }

    private fun textExpander() {
        viewModel.trackingReadMore.observe(viewLifecycleOwner) {
            binding.customViewInfoMovie.setExpandableText(
                fullText = argsMovie.infoMovie.description ?: getString(R.string.no_description),
                isExpanded = it,
                textColor = ContextCompat.getColor(requireContext(), R.color.black),
                isUnderline = it,
                onExpand = { viewModel.onReadMoreClicked() },
                onCollapse = { viewModel.onLessMoreClicked() }
            )
        }
    }
}


