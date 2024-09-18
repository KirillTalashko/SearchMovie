package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.searchmovie.R
import com.example.searchmovie.core.extension.addReadMore
import com.example.searchmovie.core.utils.ImageHelper
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.AdapterRelatedMovie

class CardMovieFragment : Fragment() {
    private var _binding: FragmentCardMovieBinding? = null
    private val binding
        get() = _binding!!
    private val exampleList = listOf("Первый, второй, третий")
    private val adapter = AdapterRelatedMovie()
    private val url = "https://static.wikia.nocookie.net/d184dacd-7f49-43f8-a316-453d75ce8752/thumbnail-down/width/1280/height/720"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.infoMovie.getReadMore().addReadMore(resources.getString(R.string.example_description_long))
        ImageHelper().getPhoto(url, binding.imageMovieInCardMovie)
        adapter.submitList(exampleList)
    }
}
