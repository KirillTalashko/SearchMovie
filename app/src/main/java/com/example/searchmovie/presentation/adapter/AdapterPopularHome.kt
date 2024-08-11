package com.example.searchmovie.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.databinding.ShowPopularMovieMainBinding

class AdapterPopularHome : ListAdapter<String,  ViewHolderPopularHome>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPopularHome {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ShowPopularMovieMainBinding.inflate(layoutInflater,parent,false)
        return ViewHolderPopularHome(view)
    }

    override fun onBindViewHolder(holder: ViewHolderPopularHome, position: Int) {
        holder.bind(getItem(position))
    }

}