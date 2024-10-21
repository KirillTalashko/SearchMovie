package com.example.searchmovie.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.core.extension.log
import com.example.searchmovie.databinding.ScreenCardPopularMovieBinding
import kotlin.math.floor

class ViewHolderPopularHome(private val binding: ScreenCardPopularMovieBinding, private val onClick: OnClickGetModel) :
    RecyclerView.ViewHolder(binding.root) {
    private var movie: Movie? = null

    init {
        binding.root.setOnClickListener {
            if (movie != null) {
                onClick.getModelMovie(movie!!)
                "Rating is HomeFragment ${movie!!.rating.imd}".log()
            }
        }
    }

    fun bind(item: Movie) {
        movie = item
        binding.textViewNameMovie.text = item.name
        binding.customViewCardShortHelpMovie.getTextRating().text =
            (floor(item.rating.kp * 10) / 10).toString()
        binding.imageViewWallpaperMovie.loadPhoto(item.poster?.url ?: "")
    }

}