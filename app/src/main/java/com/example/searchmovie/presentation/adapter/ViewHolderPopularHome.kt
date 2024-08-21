package com.example.searchmovie.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchmovie.databinding.ShowPopularMovieMainBinding

class ViewHolderPopularHome(private val binding: ShowPopularMovieMainBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        var name = item
        if (item.length > 10) {
            name = item.dropLast(item.length - 10).plus("...")
        }
        if (item != name) {
            binding.textNameCardMovie.setOnClickListener {
                binding.textNameCardMovie.text = item
            }
        }
        binding.textNameCardMovie.text = name
        Glide.with(binding.root)
            .load("https://vkplay.ru/hotbox/content_files/article/2018/6/374e750.jpeg")
            .into(binding.imageTrending)
    }
}