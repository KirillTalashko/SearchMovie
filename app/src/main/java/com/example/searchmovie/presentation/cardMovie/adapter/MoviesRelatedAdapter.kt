package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.core.model.MovieUi
import com.example.searchmovie.core.utils.OnClickGetModel
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding
import com.example.searchmovie.presentation.cardMovie.viewHolder.MoviesRelatedViewHolder

class MoviesRelatedAdapter(private val onClick: OnClickGetModel) :
    ListAdapter<MovieUi, MoviesRelatedViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieUi>() {
            override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRelatedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ScreenSimilarMovieBinding.inflate(layoutInflater,parent,false)
        return MoviesRelatedViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MoviesRelatedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}