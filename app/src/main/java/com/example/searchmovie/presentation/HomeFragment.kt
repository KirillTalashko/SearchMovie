package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.presentation.adapter.AdapterPopularHome

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load("https://i.ytimg.com/vi/7n5mf9_Q3_k/maxresdefault.jpg")
            .into(binding.imageTrailer)
        binding.listImagePopular.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        val adapter = AdapterPopularHome()
        binding.listImagePopular.adapter = adapter
        adapter.submitList(listOf("1","2","4","8"))
    }

}