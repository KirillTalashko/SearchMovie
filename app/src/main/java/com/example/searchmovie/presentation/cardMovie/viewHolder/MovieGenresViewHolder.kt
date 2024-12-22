package com.example.searchmovie.presentation.cardMovie.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ScreenButtonGenreMovieBinding
import com.example.searchmovie.presentation.modelMovie.GenresUi

class MovieGenresViewHolder(private val binding: ScreenButtonGenreMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GenresUi) {
        binding.buttonGenreMovie.text = item.name
    }
}