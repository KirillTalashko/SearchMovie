package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.network.modelsMovie.Genres
import com.example.searchmovie.databinding.ScreenButtonGenreMovieBinding
import com.example.searchmovie.presentation.cardMovie.viewHolder.MovieGenresViewHolder

class MovieGenresAdapter : ListAdapter<Genres, MovieGenresViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genres>() {
            override fun areItemsTheSame(oldItem: Genres, newItem: Genres) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Genres, newItem: Genres) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenresViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ScreenButtonGenreMovieBinding.inflate(layoutInflater, parent, false)
        return MovieGenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenresViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}