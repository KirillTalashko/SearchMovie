package com.example.searchmovie.presentation.search

import android.os.Bundle
import android.view.View
import com.example.searchmovie.databinding.FragmentSearchBinding
import com.example.searchmovie.presentation.search.adatper.MovieCardByCategoryAdapter
import com.example.searchmovie.presentation.search.adatper.MovieCategoryAdapter
import com.example.searchmovie.presentation.utils.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var adapterTypeMovie: MovieCategoryAdapter
    private lateinit var adapterCardMovieByType: MovieCardByCategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapterTypeMovie = MovieCategoryAdapter()
        adapterCardMovieByType = MovieCardByCategoryAdapter()
        binding.recyclerViewTypeMovieSearch.adapter = adapterTypeMovie

    }

    /*private fun cardSizeSettings() {
        binding.recyclerViewCardMovie.layoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 2 == 0) {
                        if (position % 4 == 0) 0 else 1
                    } else {
                        if ((position + 1) % 4 == 0) 0 else 1
                    }
                }
            }

        }
    }*/
}
