package com.example.searchmovie.presentation.search.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemMovieCardFromCategoryLargeBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.utils.extension.loadPhoto

class MovieByCategoryFirstViewHolder(private val binding: ItemMovieCardFromCategoryLargeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieUi) {
        binding.textViewMovieNameFromCategory.text = movie.name
        binding.imageViewMovieTitleFromCategory.loadPhoto(movie.poster?.url)
    }
}