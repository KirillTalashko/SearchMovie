package com.example.searchmovie.presentation.viewModel

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.searchmovie.domain.MovieRepository
import com.example.searchmovie.model.StatusRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class ViewModelRandomMovie(private val repository: MovieRepository) : ViewModel() {

    private var _trailer = MutableLiveData<StatusRequest>(StatusRequest.Loading)
    val trailer: MutableLiveData<StatusRequest>
        get() = _trailer

    fun getStatusResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            val body = repository.getRandomMovie().body()
            try {
                if (body != null) {
                    _trailer.postValue(StatusRequest.Success(body))
                } else
                    _trailer.postValue(StatusRequest.Loading)

            } catch (e: Exception) {
                _trailer.postValue(StatusRequest.Error(e))
            }
        }
    }

    fun getPhoto(context: Context, url: String?, binding: ImageView) {
        Glide.with(context)
            .load(url)
            .into(binding)
    }
}