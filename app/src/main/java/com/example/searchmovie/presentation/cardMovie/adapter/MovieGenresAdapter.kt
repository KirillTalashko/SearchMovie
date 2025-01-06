package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.databinding.ScreenButtonGenreMovieBinding
import com.example.searchmovie.presentation.cardMovie.viewHolder.MovieGenresViewHolder
import com.example.searchmovie.presentation.modelMovie.GenresUi

class MovieGenresAdapter : ListAdapter<GenresUi, MovieGenresViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenresUi>() {
            override fun areItemsTheSame(oldItem: GenresUi, newItem: GenresUi) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: GenresUi, newItem: GenresUi) =
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