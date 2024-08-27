package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.domain.MovieRepositoryImpl
import com.example.searchmovie.presentation.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
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
        /*viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is StatusRequest.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_LONG).show()
                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        restartStateButton.visibility = View.VISIBLE
                        restartStateButton.setOnClickListener {
                            viewModel.getStatusResponse()
                            restartStateButton.visibility = View.GONE
                            loadingProgressBar.visibility = View.VISIBLE
                        }
                    }
                }

                StatusRequest.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is StatusRequest.Success -> {
                    binding.apply {
                        imageMovie.visibility = View.VISIBLE
                        playCard.visibility = View.VISIBLE
                        loadingProgressBar.visibility = View.GONE
                        playCard.getTextNameView().text = it.data.name
                    }
                    ImageHelper().getPhoto(it.data.poster?.url, binding.imageMovie)
                }
            }
        }*/
        viewModel.mediator.observe(viewLifecycleOwner) {
            if (it.movieError == null) { // падает из-за ошибки NullPointerException
                binding.apply {
                    imageMovie.visibility = View.VISIBLE
                    playCard.visibility = View.VISIBLE
                    loadingProgressBar.visibility = View.GONE
                    playCard.getTextNameView().text = it.movie?.name ?: "Нет названия"
                }
                ImageHelper().getPhoto(it.movie?.poster?.url, binding.imageMovie)
            }
            if (it.movieListError == null) {
                adapter.submitList(it.listMovie)
            }
            if (it.movieError != null) {
                Toast.makeText(requireContext(), it.movieError, Toast.LENGTH_LONG).show()
                binding.apply {
                    loadingProgressBar.visibility = View.GONE
                    restartStateButton.visibility = View.VISIBLE
                    restartStateButton.setOnClickListener {
                        viewModel.getStatusResponse()
                        restartStateButton.visibility = View.GONE
                        loadingProgressBar.visibility = View.VISIBLE
                    }
                }
                if (it.movieListError != null) {
                    Toast.makeText(requireContext(), it.movieListError, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}