package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding

class AdapterRelatedMovie : ListAdapter<String, ViewHolderRelatedMovie>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
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