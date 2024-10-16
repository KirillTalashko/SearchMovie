package com.example.searchmovie.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.databinding.ScreenCardPopularMovieBinding
import com.example.network.modelsMovie.Movie



class AdapterPopularHome(private val onClick:OnClickGetModel) : ListAdapter<Movie, ViewHolderPopularHome>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPopularHome {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ScreenCardPopularMovieBinding.inflate(layoutInflater,parent,false)
        return ViewHolderPopularHome(view,onClick)
    }

    override fun onBindViewHolder(holder: ViewHolderPopularHome, position: Int) {
        holder.bind(getItem(position))
    }

}