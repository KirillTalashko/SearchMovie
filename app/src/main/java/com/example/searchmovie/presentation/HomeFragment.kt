package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.searchmovie.CenterZoomLayoutManager
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.domain.RepositoryGetTrailer
import com.example.searchmovie.presentation.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.viewModel.MyViewModelFactory
import com.example.searchmovie.presentation.viewModel.ViewModelRandomTrailer

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!


    private val viewModel: ViewModelRandomTrailer by lazy {
        ViewModelProvider(this, factory = MyViewModelFactory(repository = RepositoryGetTrailer()))[ViewModelRandomTrailer::class.java]
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
        adapter.submitList(listOf("Первому игроку приготовиться", "Матрица","Валл-и","Первому игроку приготовиться"))
        viewModel.getTrailer()
        viewModel.trailer.observe(viewLifecycleOwner){
            Glide.with(requireContext())
                .load(it?.let {it.persons[1].photo})
                .into(binding.imageMovie)
        }
    }

}