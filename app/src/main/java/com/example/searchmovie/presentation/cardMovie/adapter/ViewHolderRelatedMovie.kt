package com.example.searchmovie.presentation.cardMovie.adapter


import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.core.utils.ImageHelper
import com.example.searchmovie.databinding.ScreenRelatedMovieBinding

class ViewHolderRelatedMovie(private val binding: ScreenRelatedMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String){
        binding.textMovieInRelated.text = item
        ImageHelper().getPhoto(url,binding.imageRelatedMovie)
    }
    private val url = "https://static.wikia.nocookie.net/d184dacd-7f49-43f8-a316-453d75ce8752/thumbnail-down/width/1280/height/720"


}