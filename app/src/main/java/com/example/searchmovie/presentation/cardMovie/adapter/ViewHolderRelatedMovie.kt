package com.example.searchmovie.presentation.cardMovie.adapter


import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.core.utils.ImageHelper
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding

class ViewHolderRelatedMovie(private val binding: ScreenSimilarMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.similarMovie.getTextSimilarMovie().text = item
        ImageHelper().getPhoto(url, binding.similarMovie.getImageSimilarMovie())

    }

    private val url =
        "https://static.wikia.nocookie.net/d184dacd-7f49-43f8-a316-453d75ce8752/thumbnail-down/width/1280/height/720"
}