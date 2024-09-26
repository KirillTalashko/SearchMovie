package com.example.searchmovie.presentation.cardMovie.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.core.utils.ImageHelper
import com.example.searchmovie.databinding.ScreenSimilarMovieSecondBinding

class ViewHolderNestedSimilarMovie(private val binding: ScreenSimilarMovieSecondBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.textSimilarMovieSecond.text = item
        ImageHelper().getPhoto(url, binding.imageSimilarMovieSecond)
    }

    private val url =
        "https://static.wikia.nocookie.net/d184dacd-7f49-43f8-a316-453d75ce8752/thumbnail-down/width/1280/height/720"
}