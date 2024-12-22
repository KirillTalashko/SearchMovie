package com.example.searchmovie.presentation.search.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemMovieCardFromCategorySmallBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.utils.extension.loadPhoto

class MovieByCategorySecondViewHolder(private val binding: ItemMovieCardFromCategorySmallBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieUi) {
        binding.textViewMovieNameFromCategorySmall.text = movie.name
        binding.imageViewMovieTitleFromCategorySmall.loadPhoto(movie.poster?.url)
    }
}