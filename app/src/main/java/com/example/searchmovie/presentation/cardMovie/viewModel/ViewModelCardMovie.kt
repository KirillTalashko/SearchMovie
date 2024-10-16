package com.example.searchmovie.presentation.cardMovie.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelCardMovie : ViewModel() {

    private var _trackingReadMore = MutableLiveData(false)

    val trackingReadMore: LiveData<Boolean>
        get() = _trackingReadMore

    fun onReadMoreClicked(){
        _trackingReadMore.value = true
    }
    fun onLessMoreClicked(){
        _trackingReadMore.value = false
    }



}