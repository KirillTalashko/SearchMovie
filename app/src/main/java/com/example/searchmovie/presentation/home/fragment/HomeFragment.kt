package com.example.searchmovie.presentation.home.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.core.extension.log
import com.example.searchmovie.core.extension.showToast
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.home.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.home.viewModel.HomeFragmentStateListMovie
import com.example.searchmovie.presentation.home.viewModel.HomeFragmentStateRandomMovie
import com.example.searchmovie.presentation.home.viewModel.ViewModelRandomMovie
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val adapterMovieMain = AdapterPopularHome()
    private val currentListEmpty: Boolean
        get() = adapterMovieMain.currentList.isEmpty()

    private val inject by lazy {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: ViewModelRandomMovie by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, factory)[ViewModelRandomMovie::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate Fragment".log()
        inject
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        "onCreateView Fragment".log()
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        "onViewCreated Fragment".log()
        initRecyclerView()
        observerViewModel()
        interactionWithView()
    }

    private fun interactionWithView() {
        binding.containerPlayRandomMovie.customViewPlayCard.getImageView().setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCardMovieFragment())
        }
        binding.root.setOnRefreshListener {
            viewModel.getRandomMovie()
            viewModel.getListMovie()
        }
    }

    private fun initRecyclerView() {
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
        viewModel.stateRandomMovie.observe(viewLifecycleOwner) { randomMovie ->
            when (randomMovie) {
                is HomeFragmentStateRandomMovie.Error -> {
                    binding.root.isRefreshing = false
                    requireContext().showToast(randomMovie.error)
                }

                HomeFragmentStateRandomMovie.LoadingMovie -> {
                    binding.apply {
                        shimmerCardMovieMain.startShimmer()
                        shimmerCardMovieMain.visibility = View.VISIBLE
                    }
                }

                is HomeFragmentStateRandomMovie.SuccessMovie -> {
                    binding.root.isRefreshing = false
                    binding.apply {
                        shimmerCardMovieMain.stopShimmer()
                        shimmerCardMovieMain.visibility = View.GONE
                        containerPlayRandomMovie.containerCardMovie.visibility = View.VISIBLE
                        containerPlayRandomMovie.customViewPlayCard.getTextNameView().text =
                            randomMovie.movie.name ?: getString(R.string.no_name)
                    }
                    binding.containerPlayRandomMovie.imageViewIntroMovie.loadPhoto(
                        randomMovie.movie.poster?.url ?: ""
                    )
                }
            }
        }
        viewModel.stateListMovie.observe(viewLifecycleOwner) { listMovie ->
            when (listMovie) {
                is HomeFragmentStateListMovie.Error -> {
                    binding.root.isRefreshing = false
                    requireContext().showToast(listMovie.error)
                }

                HomeFragmentStateListMovie.LoadingListMovie -> {
                    if (currentListEmpty) {
                        binding.shimmerScrollListMovie.startShimmer()
                        binding.shimmerScrollListMovie.visibility = View.VISIBLE
                        binding.rvScrollTrendingMoviesMain.visibility = View.GONE
                    }
                }

                is HomeFragmentStateListMovie.SuccessListMovie -> {
                    binding.root.isRefreshing = false
                    if (currentListEmpty) {
                        binding.apply {
                            shimmerScrollListMovie.stopShimmer()
                            shimmerScrollListMovie.visibility = View.GONE
                            rvScrollTrendingMoviesMain.visibility = View.VISIBLE
                        }
                    }
                    val currentList = adapterMovieMain.currentList
                    adapterMovieMain.submitList(currentList.plus(listMovie.listMovie))
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy Fragment".log()
        _binding = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        "Config Fragment".log()
    }

}
