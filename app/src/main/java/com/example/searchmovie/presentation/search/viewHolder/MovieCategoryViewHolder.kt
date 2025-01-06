package com.example.searchmovie.presentation.search.viewHolder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.R
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

        binding.textViewNameMovieCategory.setTextColor(
            if (isSelected) {
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.orange
                )
            } else {
                ContextCompat.getColor(binding.root.context, R.color.black) // Цвет по умолчанию
            }
        )

        binding.viewHighlight.setBackgroundColor(
            if (isSelected) {
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.orange
                )
            } else {
                ContextCompat.getColor(binding.root.context, R.color.black)
            }
        )
    }
}
