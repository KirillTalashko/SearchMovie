package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.domain.MovieRepositoryImpl
import com.example.searchmovie.extension.log
import com.example.searchmovie.presentation.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.viewModel.StatusRequest
import com.example.searchmovie.presentation.viewModel.ViewModelFactory
import com.example.searchmovie.presentation.viewModel.ViewModelRandomMovie
import com.example.searchmovie.utils.ImageHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val adapterMovieMain = AdapterPopularHome()
    private var count = 0

    private val viewModel: ViewModelRandomMovie by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this,
            factory = ViewModelFactory(repository = MovieRepositoryImpl())
        )[ViewModelRandomMovie::class.java]
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
        binding.restartStateButton.setOnClickListener {
            viewModel.getStatusResponse()
            binding.restartStateButton.visibility = View.GONE
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
                if (!viewModel.isLoading && lastVisibleItem == totalItemCount - 3) {
                    "запрос № $count, ${viewModel.isLoading},${lastVisibleItem}".log()
                    count++
                    viewModel.getListMovieResponse()
                }
            }
        })
    }

    private fun observerViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is StatusRequest.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    binding.apply {
                        binding.restartStateButton.visibility = View.VISIBLE
                    }
                }

                StatusRequest.LoadingListMovie -> {
                    binding.shimmerScrollListMovie.startShimmer()
                    binding.shimmerScrollListMovie.visibility = View.VISIBLE
                    binding.cardMovieMain.visibility = View.GONE
                }

                StatusRequest.LoadingMovie -> {
                    binding.shimmerPlayCard.startShimmer()
                    binding.shimmerPlayCard.visibility = View.VISIBLE
                    binding.cardMovieMain.visibility = View.GONE
                }

                is StatusRequest.SuccessListMovie -> {
                    binding.shimmerScrollListMovie.stopShimmer()
                    binding.shimmerScrollListMovie.visibility = View.GONE
                    binding.scrollTrendingMoviesMain.visibility = View.VISIBLE
                    adapterMovieMain.submitList(it.listMovie)
                }

                is StatusRequest.SuccessMovie -> {
                    binding.apply {
                        shimmerPlayCard.stopShimmer()
                        shimmerPlayCard.visibility = View.GONE
                        cardMovieMain.visibility = View.VISIBLE
                        imageMovie.visibility = View.VISIBLE
                        playCard.visibility = View.VISIBLE
                        playCard.getTextNameView().text = it.movie.name ?: "Нет названия"
                    }
                    ImageHelper().getPhoto(it.movie.poster?.url, binding.imageMovie)
                }
            }
        }
    }
}
