package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.domain.MovieRepositoryImpl
import com.example.searchmovie.model.StatusRequest
import com.example.searchmovie.presentation.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.viewModel.ViewModelFactory
import com.example.searchmovie.presentation.viewModel.ViewModelRandomMovie

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
        adapter.submitList(
            listOf(
                "Первому игроку приготовиться",
                "Матрица",
                "Валл-и",
                "Первому игроку приготовиться"
            )
        )
        viewModel.getStatusResponse()
        viewModel.trailer.observe(viewLifecycleOwner) {
            when (it) {
                is StatusRequest.Error -> {
                    it.error.printStackTrace()
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.restartStateButton.visibility = View.VISIBLE
                    binding.restartStateButton.setOnClickListener {
                        viewModel.getStatusResponse()
                    }
                }

                StatusRequest.Loading -> {
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }

                is StatusRequest.Success -> {
                    val url = it.data.poster.url
                    binding.imageMovie.visibility = View.VISIBLE
                    binding.playCard.visibility = View.VISIBLE
                    binding.loadingProgressBar.visibility = View.GONE
                    viewModel.getPhoto(requireContext(), url, binding.imageMovie)
                    binding.playCard.getTextNameView().text = it.data.name
                }
            }
        }
    }
}