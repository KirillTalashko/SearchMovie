package com.example.searchmovie.presentation.search.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemMovieTypeBinding
import com.example.searchmovie.presentation.modelMovie.CategoryUi

class MovieCategoryViewHolder(
    private val binding: ItemMovieTypeBinding,
    private val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun bind(item: CategoryUi, isSelected: Boolean) {
        binding.textViewNameMovieCategory.text = item.name
        binding.viewHighlight.visibility = if (isSelected) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
