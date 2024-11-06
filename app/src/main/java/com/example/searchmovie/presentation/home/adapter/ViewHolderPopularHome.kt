package com.example.searchmovie.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.databinding.ScreenCardPopularMovieBinding
import kotlin.math.floor

class ViewHolderPopularHome(
    private val binding: ScreenCardPopularMovieBinding,
    private val onClick: OnClickGetModel
) :
    RecyclerView.ViewHolder(binding.root) {
    private var movie: MovieUi? = null

    init {
        binding.root.setOnClickListener {
            movie?.let { movie ->
                onClick.getModelMovie(movie)
            }
        }

    }

    fun bind(item: MovieUi) {
        movie = item
        binding.textViewNameMovie.text = item.name
        binding.customViewCardShortHelpMovie.getTextRating().text =
            (floor(item.rating.kp * 10) / 10).toString()
        binding.imageViewWallpaperMovie.loadPhoto(item.poster?.url)
    }

}