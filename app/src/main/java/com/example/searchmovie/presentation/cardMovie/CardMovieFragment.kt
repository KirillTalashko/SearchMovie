package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.common.extension.loadPhoto
import com.example.common.extension.reduceToDecimals
import com.example.common.extension.showToast
import com.example.common.model.ValueHolderView
import com.example.common.utils.BaseFragment
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.extension.toListMovieUi
import com.example.searchmovie.core.extension.toMovie
import com.example.searchmovie.core.extension.toMovieUi
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.AdapterRelatedMovie
import com.example.searchmovie.presentation.cardMovie.viewModel.MovieCardMovieFragmentState
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import com.example.searchmovie.presentation.customView.InfoMovie
import com.example.searchmovie.presentation.home.adapter.OnClickGetModel
import javax.inject.Inject

class CardMovieFragment :
    BaseFragment<FragmentCardMovieBinding>(FragmentCardMovieBinding::inflate), OnClickGetModel {

    private val argsMovie: CardMovieFragmentArgs by navArgs()

    private lateinit var adapterRelatedMovie: AdapterRelatedMovie

    private val inject by lazy(LazyThreadSafetyMode.NONE) {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: ViewModelCardMovie by viewModels<ViewModelCardMovie> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        interactionWithView()
    }

    private fun initRecyclerView() {
        viewModel.getListMovieByGenre(argsMovie.infoMovie.toMovieUi())
        adapterRelatedMovie = AdapterRelatedMovie(this)
        binding.rvScrollSimilarMovie.adapter = adapterRelatedMovie
        viewModel.stateListMovieByGenre.observe(viewLifecycleOwner) {
            when (it) {
                is MovieCardMovieFragmentState.Error -> {
                    requireContext().showToast(it.error)
                }

                MovieCardMovieFragmentState.LoadingRelatedMovies -> {
                    Unit
                }

                is MovieCardMovieFragmentState.SuccessRelatedMovies -> {
                    val currentList = adapterRelatedMovie.currentList
                    adapterRelatedMovie.submitList(currentList.plus(it.listMovie.toListMovieUi()))
                }
            }
        }
        binding.rvScrollSimilarMovie.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        binding.rvScrollSimilarMovie.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!viewModel.getIsLoading() && lastVisibleItem == totalItemCount - 3) {
                        viewModel.getListMovieByGenre(argsMovie.infoMovie.toMovieUi())
                    }
                }
            }
        )
    }

    private fun interactionWithView() {
        binding.imageViewPosterMovie.loadPhoto(argsMovie.infoMovie.poster?.url)
        setCharacteristics()
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

    private fun setCharacteristics() {
        binding.apply {
            customViewInfoMovie.setDataInfoMovie(
                name = argsMovie.infoMovie.name ?: getString(R.string.no_name),
                year = argsMovie.infoMovie.year.toString(),
                genres = argsMovie.infoMovie.genres ?: emptyList()
            )

            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.TIME,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_time
                    ),
                    firstText = argsMovie.infoMovie.duration.toString(),
                    secondText = getString(R.string.minutes)
                )
            )

            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.RATING_IMDB,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_star_gray
                    ),
                    firstText = argsMovie.infoMovie.rating.imd.reduceToDecimals().toString(),
                    secondText = getString(R.string.text_rating_imdb)
                )
            )
            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.RATING_KP,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_star_gray
                    ),
                    firstText = argsMovie.infoMovie.rating.kp.reduceToDecimals().toString(),
                    secondText = getString(R.string.text_rating_kp)
                )
            )
        }
    }

    override fun getModelMovie(movie: MovieUi) {
        findNavController().navigate(
            CardMovieFragmentDirections.cardMovieFragmentToCardMovieFragment(
                infoMovie = movie.toMovie()
            )
        )
    }
}


