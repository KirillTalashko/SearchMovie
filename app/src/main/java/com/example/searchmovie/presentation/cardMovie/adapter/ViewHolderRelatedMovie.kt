package com.example.searchmovie.presentation.cardMovie.adapter


import androidx.recyclerview.widget.RecyclerView
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding

class ViewHolderRelatedMovie(private val binding: ScreenSimilarMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.imageSimilarMovieSecond.loadPhoto(item.poster?.url ?: "")
        binding.textSimilarMovieSecond.text = item.name
    }
}