package com.example.searchmovie.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchmovie.domain.MyRepository
import com.example.searchmovie.model.RandomTrailerResponse

class ViewModelRandomTrailer(private val repository: MyRepository) : ViewModel() {

    private val _trailer = MutableLiveData<RandomTrailerResponse?>()
    val trailer: MutableLiveData<RandomTrailerResponse?>
        get() = _trailer

    fun getTrailer() {
        repository.getTrailer { response, throwable ->
            if (throwable != null)
                throw InterruptedException(throwable.message)
            else if (response != null)
                _trailer.value = response
        }
    }


}