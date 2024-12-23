package com.example.searchmovie.presentation.search.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.databinding.ItemMovieTypeBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.search.viewHolder.MovieCategoryViewHolder

class MovieCategoryAdapter : ListAdapter<MovieUi, MovieCategoryViewHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieUi>() {
            override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemMovieTypeBinding.inflate(layoutInflater, parent, false)
        return MovieCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieCategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}