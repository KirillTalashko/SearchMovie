package com.example.searchmovie.presentation.search.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemMovieCardFromCategoryLargeBinding
import com.example.searchmovie.databinding.ItemMovieCardFromCategorySmallBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.search.viewHolder.MovieByCategoryFirstViewHolder
import com.example.searchmovie.presentation.search.viewHolder.MovieByCategorySecondViewHolder


class MovieCardByCategoryAdapter :
    ListAdapter<MovieUi, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieUi>() {
            override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi) =
                oldItem == newItem
        }
        private const val VIEW_HOLDER_FIRST = 0
        private const val VIEW_HOLDER_SECOND = 1
    }


    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            if (position / 2 % 2 == 0) {
                VIEW_HOLDER_FIRST
            } else {
                VIEW_HOLDER_SECOND
            }
        } else {
            if ((position - 1) / 2 % 2 == 0) {
                VIEW_HOLDER_FIRST
            } else {
                VIEW_HOLDER_SECOND
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_HOLDER_FIRST -> {
                val view =
                    ItemMovieCardFromCategoryLargeBinding.inflate(layoutInflater, parent, false)
                MovieByCategoryFirstViewHolder(view)
            }

            else -> {
                val view =
                    ItemMovieCardFromCategorySmallBinding.inflate(layoutInflater, parent, false)
                MovieByCategorySecondViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is MovieByCategoryFirstViewHolder -> holder.bind(item)
            is MovieByCategorySecondViewHolder -> holder.bind(item)
        }
    }
}
