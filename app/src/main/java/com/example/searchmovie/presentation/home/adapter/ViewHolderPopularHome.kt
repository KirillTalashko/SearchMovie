package com.example.searchmovie.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ScreenCardPopularMovieBinding
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.loadPhoto
import kotlin.math.floor

class ViewHolderPopularHome(private val binding: ScreenCardPopularMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        val rating = floor(item.rating.kp * 10) / 10
        binding.textViewNameMovie.text = item.name
        binding.customViewCardShortHelpMovie.getTextRating().text = rating.toString()
        binding.imageViewWallpaperMovie.loadPhoto(item.poster?.url ?: "")
    }
}