package com.example.searchmovie.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.databinding.ShowPopularMovieMainBinding
import com.example.searchmovie.modelsMovie.Movie
import kotlin.math.floor

class ViewHolderPopularHome(private val binding: ShowPopularMovieMainBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        val rating = floor(item.rating.kp * 10) / 10
        binding.textNameCardMovie.text = item.name
        binding.cardRating.getTextRating().text = rating.toString()
        binding.imageTrending.loadPhoto(item.poster?.url)
    }
}