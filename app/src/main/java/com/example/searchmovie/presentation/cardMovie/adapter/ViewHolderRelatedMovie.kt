package com.example.searchmovie.presentation.cardMovie.adapter


import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding
import com.example.searchmovie.presentation.home.adapter.OnClickGetModel

class ViewHolderRelatedMovie(
    private val binding: ScreenSimilarMovieBinding,
    private val onClick: OnClickGetModel
) :
    RecyclerView.ViewHolder(binding.root) {

    private var movie: Movie? = null

    init {
        binding.root.setOnClickListener {
            movie?.let { movie ->
                onClick.getModelMovie(movie)
            }
        }
    }

    fun bind(item: Movie) {
        movie = item
        binding.imageSimilarMovieSecond.loadPhoto(item.poster?.url)
        binding.textSimilarMovieSecond.text = item.name
    }
}