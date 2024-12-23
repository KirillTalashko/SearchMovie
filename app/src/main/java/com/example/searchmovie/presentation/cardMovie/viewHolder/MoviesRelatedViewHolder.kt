package com.example.searchmovie.presentation.cardMovie.viewHolder


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ScreenSimilarMovieBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.utils.OnClickGetModel
import com.example.searchmovie.presentation.utils.extension.loadPhoto

class MoviesRelatedViewHolder(
    private val binding: ScreenSimilarMovieBinding,
    private val onClick: OnClickGetModel
) :
    RecyclerView.ViewHolder(binding.root) {

    private var movie: MovieUi? = null

    init {
        binding.root.setOnClickListener {
            movie?.let { movie ->
                onClick.getMovieModel(movie)
            }
        }
    }

    fun bind(item: MovieUi) {
        movie = item
        binding.imageSimilarMovieSecond.loadPhoto(item.poster?.url)
        binding.textSimilarMovieSecond.text = item.name
        if (onClick.isLocalData() == true) {
            binding.imageViewLocalDataSimilarMovie.visibility = View.VISIBLE
        } else {
            binding.imageViewLocalDataSimilarMovie.visibility = View.GONE
        }
    }
}