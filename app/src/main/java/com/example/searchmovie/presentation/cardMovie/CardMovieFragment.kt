package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.databinding.InformationMovieBinding

class CardMovieFragment : Fragment() {
    //private var _binding: FragmentCardMovieBinding? = null
    private var _binding: InformationMovieBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InformationMovieBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.description.setOnClickListener{
            onClickListener()
        }
    }

    private fun onClickListener() {
        binding.descriptionMovie.setText("Rey (Daisy Ridley) finally manages to find the legendary Jedi knight, Luke Skywalker (Mark Hamill) on an island with a magical aura. The heroes of The Force Awakens including Leia.",binding.descriptionMovie.getTextView())
    }
}