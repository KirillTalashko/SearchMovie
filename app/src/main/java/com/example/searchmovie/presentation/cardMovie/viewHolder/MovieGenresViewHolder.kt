package com.example.searchmovie.presentation.cardMovie.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.network.modelsMovie.Genres
import com.example.searchmovie.databinding.ScreenButtonGenreMovieBinding

class MovieGenresViewHolder(private val binding: ScreenButtonGenreMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Genres) {
        binding.buttonGenreMovie.text = item.genresName
    }
}