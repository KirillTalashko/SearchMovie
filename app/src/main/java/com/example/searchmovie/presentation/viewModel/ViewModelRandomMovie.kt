package com.example.searchmovie.presentation.viewModel

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.model.StatusRequest

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var _trailer = MutableLiveData<StatusRequest>(StatusRequest.Loading)
    val trailer: MutableLiveData<StatusRequest>
        get() = _trailer

    fun getStatusResponse() {
        repository.getRandomMovie { response, throwable ->
            if (throwable != null)
                _trailer.value = StatusRequest.Error(throwable)
            else if (response != null)
                _trailer.value = StatusRequest.Success(response)
        }
    }

    fun getPhoto(context: Context, url: String?, binding: ImageView) {
        Glide.with(context)
            .load(url)
            .into(binding)
    }
}