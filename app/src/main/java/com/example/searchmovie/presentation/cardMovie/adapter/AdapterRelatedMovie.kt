package com.example.searchmovie.presentation.cardMovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding
import com.example.searchmovie.databinding.ScreenSimilarMovieSecondBinding

class AdapterRelatedMovie : ListAdapter<String, ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }

    private val VIEW_HOLDER_FIRST = 0
    private val VIEW_HOLDER_LAST = 1

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            VIEW_HOLDER_LAST
        } else VIEW_HOLDER_FIRST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_HOLDER_FIRST -> {
                val view = ScreenSimilarMovieBinding.inflate(layoutInflater, parent, false)
                ViewHolderRelatedMovie(view)
            }

            else -> {
                val view = ScreenSimilarMovieSecondBinding.inflate(layoutInflater, parent, false)
                ViewHolderNestedSimilarMovie(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ViewHolderRelatedMovie -> holder.bind(item)
            is ViewHolderNestedSimilarMovie -> holder.bind(item)
        }
    }
}