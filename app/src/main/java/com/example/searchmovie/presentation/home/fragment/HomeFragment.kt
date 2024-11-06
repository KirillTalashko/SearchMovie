package com.example.searchmovie.presentation.home.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.common.extension.showToast
import com.example.common.utils.BaseFragment
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.extension.toMovie
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.home.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.home.adapter.OnClickGetModel
import com.example.searchmovie.presentation.home.viewModel.MovieMainFragmentState
import com.example.searchmovie.presentation.home.viewModel.MoviesMainFragmentState
import com.example.searchmovie.presentation.home.viewModel.ViewModelRandomMovie
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnClickGetModel {

    private lateinit var adapterMovieMain: AdapterPopularHome
    private val currentListEmpty: Boolean
        get() = adapterMovieMain.currentList.isEmpty()

    private val inject by lazy {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: ViewModelRandomMovie by viewModels<ViewModelRandomMovie> { factory }

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
            viewModel.getRandomMovie()
            viewModel.getListMovie()
        }
    }

    private fun initRecyclerView() {
        adapterMovieMain = AdapterPopularHome(this)
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
                    viewModel.getListMovie()
                }
            }
        })
    }

    private fun observerViewModel() {
        viewModel.stateRandomMovie.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieMainFragmentState.Error -> {
                    binding.root.isRefreshing = false
                    requireContext().showToast(state.error)
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

                        containerPlayRandomMovie.customViewPlayCard.getTextNameView().text =
                            state.movie.name ?: getString(R.string.no_name)
                        containerPlayRandomMovie.imageViewIntroMovie.loadPhoto(
                            state.movie.poster?.url
                        )
                        containerPlayRandomMovie.containerCardMovie.setOnClickListener {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToCardMovieFragment(
                                    infoMovie = state.movie.toMovie()
                                )
                            )
                        }
                    }
                }
            }
            viewModel.stateListMovie.observe(viewLifecycleOwner) { listMovie ->
                when (listMovie) {
                    is MoviesMainFragmentState.Error -> {
                        binding.root.isRefreshing = false
                        requireContext().showToast(listMovie.error)
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
                        if (!listMovie.isLoading) {
                            requireContext().showToast("Данные взяты с базы данных")
                        }
                        val currentList = adapterMovieMain.currentList
                        adapterMovieMain.submitList(currentList.plus(listMovie.listMovie))
                    }
                }
            }
        }
    }

    override fun getModelMovie(movie: MovieUi) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToCardMovieFragment(
                infoMovie = movie.toMovie()
            )
        )
    }

}
