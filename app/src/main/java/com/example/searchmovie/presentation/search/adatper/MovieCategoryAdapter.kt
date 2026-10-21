package com.example.searchmovie.presentation.search.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.searchmovie.databinding.ItemMovieTypeBinding
import com.example.searchmovie.presentation.modelMovie.CategoryUi
import com.example.searchmovie.presentation.search.viewHolder.MovieCategoryViewHolder

class MovieCategoryAdapter(
    private val onCategorySelected: (CategoryUi) -> Unit
) : ListAdapter<CategoryUi, MovieCategoryViewHolder>(DIFF_CALLBACK) {

    private var selectedPosition: Int = 0

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryUi>() {
            override fun areItemsTheSame(oldItem: CategoryUi, newItem: CategoryUi) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: CategoryUi, newItem: CategoryUi) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemMovieTypeBinding.inflate(layoutInflater, parent, false)
        return MovieCategoryViewHolder(view) { position ->

            val previousPosition = selectedPosition
            selectedPosition = position

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            onCategorySelected(getItem(position))
        }

    }

    override fun onBindViewHolder(holder: MovieCategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position == selectedPosition)
    }
}