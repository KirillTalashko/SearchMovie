package com.example.searchmovie.presentation.cardMovie.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.network.modelsMovie.Genres
import com.example.searchmovie.databinding.ScreenButtonGenreMovieBinding

class ViewHolderGenreMovie(private val binding: ScreenButtonGenreMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Genres) {
        binding.buttonGenreMovie.text = item.genresName
    }
}