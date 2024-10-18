package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.network.modelsMovie.Genres
import com.example.searchmovie.databinding.ScreenButtonGenreMovieBinding

class AdapterGenreMovie : ListAdapter<Genres, ViewHolderGenreMovie>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genres>() {
            override fun areItemsTheSame(oldItem: Genres, newItem: Genres) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Genres, newItem: Genres) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGenreMovie {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ScreenButtonGenreMovieBinding.inflate(layoutInflater, parent, false)
        return ViewHolderGenreMovie(view)
    }

    override fun onBindViewHolder(holder: ViewHolderGenreMovie, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}