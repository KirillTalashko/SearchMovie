package com.example.searchmovie.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.OnClickGetModel
import com.example.searchmovie.databinding.ScreenCardPopularMovieBinding
import com.example.searchmovie.presentation.home.viewHolder.MoviesPopularViewHolder


class MoviesPopularAdapter(private val onClick: OnClickGetModel) :
    ListAdapter<MovieUi, MoviesPopularViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieUi>() {
            override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesPopularViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ScreenCardPopularMovieBinding.inflate(layoutInflater,parent,false)
        return MoviesPopularViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MoviesPopularViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}