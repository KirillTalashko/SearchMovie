package com.example.searchmovie.presentation.search.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemMovieCardFromCategoryLargeBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.utils.OnClickGetModel
import com.example.searchmovie.presentation.utils.extension.loadPhoto

class MovieByCategoryFirstViewHolder(
    private val binding: ItemMovieCardFromCategoryLargeBinding,
    private val onClickGetMovie: OnClickGetModel,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var movie: MovieUi? = null

    init {
        binding.root.setOnClickListener {
            movie?.let { movie -> onClickGetMovie.getMovieModel(movie) }
        }
    }

    fun bind(item: MovieUi) {
        movie = item
        binding.textViewMovieNameFromCategory.text = item.name
        binding.imageViewMovieTitleFromCategory.loadPhoto(item.poster?.url)
    }
}