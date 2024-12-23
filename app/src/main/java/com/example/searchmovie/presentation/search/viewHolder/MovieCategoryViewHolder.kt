package com.example.searchmovie.presentation.search.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemMovieTypeBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi

class MovieCategoryViewHolder(private val binding: ItemMovieTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieUi) {
        when (item.type) {
            1 -> {
                binding.textViewNameMovieType.text = "криминал"
            }

            else -> binding.textViewNameMovieType.text = "драмма"
        }
    }
}