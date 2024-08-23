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
import com.example.searchmovie.model.StatusRequest
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
        viewModel.movie.observe(viewLifecycleOwner) {
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
                    ImageHelper().getPhoto(it.data.poster.url, binding.imageMovie)
                }
            }
        }
        viewModel.listMovie.observe(viewLifecycleOwner){
            adapter.submitList(it.movie)
        }
    }
}