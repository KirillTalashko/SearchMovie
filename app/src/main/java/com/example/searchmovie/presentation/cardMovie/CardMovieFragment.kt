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
import com.example.common.extension.reduceToDecimals
import com.example.common.model.ValueHolderView
import com.example.logic.state.MovieCardMovieFragmentState
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.MoviesRelatedAdapter
import com.example.searchmovie.presentation.cardMovie.viewModel.CardMovieFragmentViewModel
import com.example.searchmovie.presentation.customView.MovieInfoCustomView
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.utils.BaseFragment
import com.example.searchmovie.presentation.utils.OnClickGetModel
import com.example.searchmovie.presentation.utils.extension.loadPhoto
import com.example.searchmovie.presentation.utils.extension.toListMovieUi
import com.example.searchmovie.presentation.utils.extension.toMovieLogic
import javax.inject.Inject

class CardMovieFragment :
    BaseFragment<FragmentCardMovieBinding>(FragmentCardMovieBinding::inflate), OnClickGetModel {

    private val argsMovie: CardMovieFragmentArgs by navArgs()

    private lateinit var adapterRelatedMovie: MoviesRelatedAdapter

    private val inject by lazy(LazyThreadSafetyMode.NONE) {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: CardMovieFragmentViewModel by viewModels<CardMovieFragmentViewModel> { factory }
    private var isLocalDate: Boolean? = null

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
        viewModel.getMovies(argsMovie.infoMovie.toMovieLogic())
        adapterRelatedMovie = MoviesRelatedAdapter(this)
        binding.rvScrollSimilarMovie.adapter = adapterRelatedMovie
        viewModel.stateMoviesByGenre.observe(viewLifecycleOwner) { moviesState ->
            when (moviesState) {
                is MovieCardMovieFragmentState.Error -> {
                    Unit
                }

                MovieCardMovieFragmentState.LoadingMoviesRelated -> {
                    Unit
                }

                is MovieCardMovieFragmentState.SuccessMoviesRelated -> {
                    val movies = moviesState.movies.toListMovieUi()
                    val currentList = adapterRelatedMovie.currentList
                    adapterRelatedMovie.submitList(currentList.plus(movies))
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
                        viewModel.getMovies(argsMovie.infoMovie.toMovieLogic())
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
                display = MovieInfoCustomView.DisplayOptionsCustomView.TIME,
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
                display = MovieInfoCustomView.DisplayOptionsCustomView.RATING_IMDB,
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
                display = MovieInfoCustomView.DisplayOptionsCustomView.RATING_KP,
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

    override fun getMovieModel(movie: MovieUi) {
        findNavController().navigate(
            CardMovieFragmentDirections.cardMovieFragmentToCardMovieFragment(
                infoMovie = movie
            )
        )
    }

    override fun isLocalData(): Boolean? {
        return isLocalDate
    }
}


