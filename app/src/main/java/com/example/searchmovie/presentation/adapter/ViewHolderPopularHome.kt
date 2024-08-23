package com.example.searchmovie.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ShowPopularMovieMainBinding
import com.example.searchmovie.model.Movie
import com.example.searchmovie.utils.ImageHelper
import kotlin.math.floor

class ViewHolderPopularHome(private val binding: ShowPopularMovieMainBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        val rating = floor(item.rating.kp * 10) / 10
        /*if (item.name?.length!! > 10) {
            name = item.dropLast(item.length - 10).plus("...")
        }
        if (item != name) {
            binding.textNameCardMovie.setOnClickListener {
                binding.textNameCardMovie.text = item
            }
        }*/
        binding.textNameCardMovie.text = item.name
        binding.cardRating.getTextRating().text = rating.toString()
        ImageHelper().getPhoto(item.poster.url, binding.imageTrending)

    }
}