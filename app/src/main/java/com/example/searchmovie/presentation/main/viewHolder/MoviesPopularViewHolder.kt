package com.example.searchmovie.presentation.main.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.common.extension.loadPhoto
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.OnClickGetModel
import com.example.searchmovie.databinding.ScreenCardPopularMovieBinding
import kotlin.math.floor

class MoviesPopularViewHolder(
    private val binding: ScreenCardPopularMovieBinding,
    private val onClick: OnClickGetModel,
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
        binding.textViewNameMovie.text = item.name
        binding.customViewCardShortHelpMovie.getMovieRatingTextView().text =
            (floor(item.rating.kp * 10) / 10).toString()
        binding.imageViewWallpaperMovie.loadPhoto(item.poster?.url)
        if (onClick.isLocalData() == true) {
            binding.imageViewLocalDateMovies?.visibility = View.VISIBLE
        } else {
            binding.imageViewLocalDateMovies?.visibility = View.GONE
        }
    }

}