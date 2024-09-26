package com.example.searchmovie.presentation.home.fragment

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
import com.example.searchmovie.core.extension.showToast
import com.example.searchmovie.core.utils.ImageHelper
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.home.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.home.viewModel.HomeFragmentState
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
        inject
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observerViewModel()
        interactionWithView()
    }

    private fun interactionWithView() {
        binding.cardViewMovie.restartStateButton.setOnClickListener {
            viewModel.getRandomMovie()
        }
        binding.cardViewMovie.playCard.getImageView().setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCardMovieFragment())
        }
    }

    private fun initRecyclerView() {
        binding.scrollTrendingMoviesMain.layoutManager = CenterZoomLayoutManager(requireContext())
        binding.scrollTrendingMoviesMain.adapter = adapterMovieMain
        binding.scrollTrendingMoviesMain.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    binding.scrollTrendingMoviesMain.layoutManager as CenterZoomLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!viewModel.getIsLoading() && lastVisibleItem == totalItemCount - 3) {
                    viewModel.getListMovie()
                }
            }
        })
    }

    private fun observerViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is HomeFragmentState.Error -> {
                    //TODO("Сделать отдельный экран для этого состояния")
                    requireContext().showToast(it.error)
                    binding.cardViewMovie.restartStateButton.visibility = View.VISIBLE
                }

                HomeFragmentState.LoadingListMovie -> {
                    if (currentListEmpty) {
                        binding.shimmerScrollListMovie.startShimmer()
                        binding.shimmerScrollListMovie.visibility = View.VISIBLE
                        binding.scrollTrendingMoviesMain.visibility = View.GONE
                    }
                }

                HomeFragmentState.LoadingMovie -> {
                    binding.apply {
                        cardViewMovie.restartStateButton.visibility = View.GONE
                        shimmerPlayCard.startShimmer()
                        shimmerPlayCard.visibility = View.VISIBLE
                    }
                }

                is HomeFragmentState.SuccessListMovie -> {
                    if (currentListEmpty) {
                        binding.apply {
                            shimmerScrollListMovie.stopShimmer()
                            shimmerScrollListMovie.visibility = View.GONE
                            scrollTrendingMoviesMain.visibility = View.VISIBLE
                        }
                    }
                    val currentList = adapterMovieMain.currentList
                    adapterMovieMain.submitList(currentList.plus(it.listMovie))
                }

                is HomeFragmentState.SuccessMovie -> {
                    binding.apply {
                        shimmerPlayCard.stopShimmer()
                        shimmerPlayCard.visibility = View.GONE
                        cardViewMovie.cardMovieMain.visibility = View.VISIBLE
                        cardViewMovie.playCard.getTextNameView().text =
                            it.movie.name ?: getString(R.string.no_name)
                    }
                    ImageHelper().getPhoto(it.movie.poster?.url, binding.cardViewMovie.imageMovie)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
