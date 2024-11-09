package com.example.searchmovie.presentation.cardMovie.viewHolder


import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.OnClickGetModel
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding

class MoviesRelatedViewHolder(
    private val binding: ScreenSimilarMovieBinding,
    private val onClick: OnClickGetModel
) :
    RecyclerView.ViewHolder(binding.root) {

    private var movie: MovieUi? = null

    init {
        binding.root.setOnClickListener {
            movie?.let { movie ->
                onClick.getMovieModel(movie)
            }
        }
    }

    fun bind(item: MovieUi) {
        movie = item
        binding.imageSimilarMovieSecond.loadPhoto(item.poster?.url)
        binding.textSimilarMovieSecond.text = item.name
    }
}