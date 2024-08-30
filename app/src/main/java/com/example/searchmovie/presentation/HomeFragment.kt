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


    private val viewModel: ViewModelRandomMovie by lazy {
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
        binding.scrollTrendingMoviesMain.layoutManager = CenterZoomLayoutManager(requireContext())
        val adapter = AdapterPopularHome()
        binding.scrollTrendingMoviesMain.adapter = adapter
        binding.restartStateButton.setOnClickListener {
            viewModel.getStatusResponse()
            binding.restartStateButton.visibility = View.GONE
        }
        binding.scrollTrendingMoviesMain.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.isLoading = true
                val totalItemCount = CenterZoomLayoutManager(requireContext()).itemCount
                val lastVisibleItem = CenterZoomLayoutManager(requireContext()).findLastVisibleItemPosition()
                if (!viewModel.isLoading && lastVisibleItem == totalItemCount - 3) {
                    "isLoading = true".log()
                    viewModel.getStatusResponse()
                }
            }
        })
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is StatusRequest.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    binding.apply {
                        binding.loadingProgressBar.visibility = View.GONE
                        binding.restartStateButton.visibility = View.VISIBLE
                    }
                }

                StatusRequest.LoadingListMovie -> {}

                StatusRequest.LoadingMovie -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is StatusRequest.SuccessListMovie -> {
                    adapter.submitList(it.listMovie)
                }

                is StatusRequest.SuccessMovie -> {
                    binding.apply {
                        imageMovie.visibility = View.VISIBLE
                        playCard.visibility = View.VISIBLE
                        loadingProgressBar.visibility = View.GONE
                        playCard.getTextNameView().text = it.movie.name ?: "Нет названия"
                    }
                    ImageHelper().getPhoto(it.movie.poster?.url, binding.imageMovie)
                }
            }
        }
    }
}
