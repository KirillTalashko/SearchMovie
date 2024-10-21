package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.network.modelsMovie.Movie
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding

class AdapterRelatedMovie : ListAdapter<Movie, ViewHolderRelatedMovie>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRelatedMovie {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ScreenSimilarMovieBinding.inflate(layoutInflater,parent,false)
        return ViewHolderRelatedMovie(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRelatedMovie, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}