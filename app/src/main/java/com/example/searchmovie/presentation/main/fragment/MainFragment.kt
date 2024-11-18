package com.example.searchmovie.presentation.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.common.extension.log
import com.example.common.utils.BaseFragment
import com.example.common.utils.DataErrorOrigin
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.ErrorDialog
import com.example.searchmovie.core.utils.OnClickGetModel
import com.example.searchmovie.databinding.FragmentMainBinding
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.main.adapter.MoviesPopularAdapter
import com.example.searchmovie.presentation.main.state.MovieMainFragmentState
import com.example.searchmovie.presentation.main.state.MoviesMainFragmentState
import com.example.searchmovie.presentation.main.viewModel.ViewModelRandomMovie
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    OnClickGetModel {

    private lateinit var adapterMovieMain: MoviesPopularAdapter
    private val currentListEmpty: Boolean
        get() = adapterMovieMain.currentList.isEmpty()

    private val inject by lazy {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var errorDialogMovie: ErrorDialog

    @Inject
    lateinit var errorDialogMovies: ErrorDialog

    private val viewModel: ViewModelRandomMovie by viewModels<ViewModelRandomMovie> { factory }

    private var isLocalDate: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observerViewModel()
        interactionWithView()
    }

    private fun interactionWithView() {
        binding.root.setOnRefreshListener {
            viewModel.getMovie()
            viewModel.getMovies()
        }
        errorDialogMovie = ErrorDialog(true, { viewModel.getMovie() })
        errorDialogMovies = ErrorDialog(false, { viewModel.getMovies() })
    }

    private fun initRecyclerView() {
        adapterMovieMain = MoviesPopularAdapter(this)
        binding.rvScrollTrendingMoviesMain.layoutManager = CenterZoomLayoutManager(requireContext())
        binding.rvScrollTrendingMoviesMain.adapter = adapterMovieMain
        binding.rvScrollTrendingMoviesMain.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    binding.rvScrollTrendingMoviesMain.layoutManager as CenterZoomLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!viewModel.getIsLoading() && lastVisibleItem == totalItemCount - 3) {
                    viewModel.getMovies()
                }
            }
        })
    }

    private fun observerViewModel() {
        viewModel.stateRandomMovie.observe(viewLifecycleOwner) { movieState ->
            when (movieState) {
                is MovieMainFragmentState.Error -> {
                    binding.root.isRefreshing = false
                    if (movieState.dataErrorOrigin == DataErrorOrigin.NETWORK) {
                        errorDialogMovie.show(childFragmentManager, ErrorDialog.TAG)
                    }
                }

                MovieMainFragmentState.LoadingMovie -> {
                    binding.apply {
                        shimmerCardMovieMain.startShimmer()
                        shimmerCardMovieMain.visibility = View.VISIBLE
                    }
                }

                is MovieMainFragmentState.SuccessMovie -> {
                    binding.root.isRefreshing = false
                    binding.apply {
                        shimmerCardMovieMain.stopShimmer()
                        shimmerCardMovieMain.visibility = View.GONE
                        containerPlayRandomMovie.containerCardMovie.visibility = View.VISIBLE
                        if (movieState.isLocalDate) {
                            containerPlayRandomMovie.imageViewLocalDateMovie.visibility =
                                View.VISIBLE
                        }
                        containerPlayRandomMovie.customViewPlayCard.getMovieNameTextView().text =
                            movieState.movie.name ?: getString(R.string.no_name)
                        containerPlayRandomMovie.imageViewIntroMovie.loadPhoto(
                            movieState.movie.poster?.url
                        )
                        containerPlayRandomMovie.containerCardMovie.setOnClickListener {
                            findNavController().navigate(
                                MainFragmentDirections.actionMainFragmentToCardMovieFragment(
                                    infoMovie = movieState.movie
                                )
                            )
                        }
                    }
                }
            }
        }
        viewModel.stateListMovie.observe(viewLifecycleOwner) { moviesState ->
            when (moviesState) {
                is MoviesMainFragmentState.Error -> {
                    binding.root.isRefreshing = false
                    if (moviesState.dataErrorOrigin == DataErrorOrigin.NETWORK) {
                        errorDialogMovies.show(childFragmentManager, ErrorDialog.TAG)
                    }
                }

                MoviesMainFragmentState.LoadingListMovie -> {
                    if (currentListEmpty) {
                        binding.apply {
                            shimmerScrollListMovie.startShimmer()
                            shimmerScrollListMovie.visibility = View.VISIBLE
                            rvScrollTrendingMoviesMain.visibility = View.GONE
                        }
                    }
                }

                is MoviesMainFragmentState.SuccessListMovie -> {
                    "isLoading ${moviesState.isLocalData}".log()
                    binding.root.isRefreshing = false
                    if (currentListEmpty) {
                        binding.apply {
                            shimmerScrollListMovie.stopShimmer()
                            shimmerScrollListMovie.visibility = View.GONE
                            rvScrollTrendingMoviesMain.visibility = View.VISIBLE
                        }
                    }
                    isLocalDate = moviesState.isLocalData
                    val currentList = adapterMovieMain.currentList
                    adapterMovieMain.submitList(currentList.plus(moviesState.listMovie))
                }
            }
        }
    }


    override fun getMovieModel(movie: MovieUi) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToCardMovieFragment(
                infoMovie = movie
            )
        )
    }

    override fun isLocalData(): Boolean? {
        return isLocalDate
    }

}
