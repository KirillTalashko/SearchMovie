package com.example.searchmovie.presentation.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.common.extension.log
import com.example.common.model.DialogInfo
import com.example.common.utils.BaseFragment
import com.example.common.utils.Core
import com.example.common.utils.IntervalTimer
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.OnClickGetModel
import com.example.searchmovie.databinding.FragmentMainBinding
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.main.adapter.MoviesPopularAdapter
import com.example.searchmovie.presentation.main.state.MovieMainFragmentState
import com.example.searchmovie.presentation.main.state.MoviesMainFragmentState
import com.example.searchmovie.presentation.main.viewModel.ViewModelRandomMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate),
    OnClickGetModel {

    private lateinit var adapterMovieMain: MoviesPopularAdapter
    private val currentListEmpty: Boolean
        get() = adapterMovieMain.currentList.isEmpty()

    private val inject by lazy(LazyThreadSafetyMode.NONE) {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var errorManager: ErrorManager

    private val viewModel: ViewModelRandomMovie by viewModels<ViewModelRandomMovie> { factory }

    private var isLocalDate: Boolean? = null

    private var previousNetworkStatus: Boolean? = null

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

        errorManager.networkStatus.observe(viewLifecycleOwner) { networkStatus ->
            lifecycleScope.launch(Dispatchers.Main) {
                "CHECK $networkStatus".log()
                "previous $previousNetworkStatus".log()
                if (networkStatus != previousNetworkStatus) {
                    if (networkStatus && isLocalDate == true) {
                        errorManager.showDialogGetLocalData(
                            DialogInfo(
                                title = getString(R.string.connect_internet),
                                description = getString(R.string.show_selection_of_movies_from_network),
                                actionPositiveFirst = { viewModel.getMovie() },
                                actionPositiveSecond = { viewModel.getMovies() },
                            )
                        )
                        IntervalTimer.counterReset()
                    }
                    if (!networkStatus && Core.firstLaunch ||
                        !networkStatus && isLocalDate == false
                    ) {
                        errorManager.showDialogGetLocalData(
                            DialogInfo(
                                title = getString(R.string.no_internet),
                                description = getString(R.string.show_selection_of_movies_from_database),
                                actionPositiveFirst = { viewModel.getLocalMovie() },
                                actionPositiveSecond = { viewModel.getLocalMovies() },
                            )
                        )
                        Core.firstLaunch = false
                    }
                    previousNetworkStatus = networkStatus
                }
            }
        }
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
                        if (isLocalDate == true) {
                            containerPlayRandomMovie.imageViewLocalDateMovie.visibility =
                                View.VISIBLE
                        } else {
                            containerPlayRandomMovie.imageViewLocalDateMovie.visibility =
                                View.GONE
                        }
                        containerPlayRandomMovie.customViewPlayCard.getMovieNameTextView().text =
                            movieState.movieUi.name ?: getString(R.string.no_name)
                        containerPlayRandomMovie.imageViewIntroMovie.loadPhoto(
                            movieState.movieUi.poster?.url
                        )
                        containerPlayRandomMovie.containerCardMovie.setOnClickListener {
                            findNavController().navigate(
                                MainFragmentDirections.actionMainFragmentToCardMovieFragment(
                                    infoMovie = movieState.movieUi
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
