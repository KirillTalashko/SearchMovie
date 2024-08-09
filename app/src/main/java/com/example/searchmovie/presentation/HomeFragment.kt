package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.searchmovie.CenterZoomLayoutManager
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
            .load("https://s3-alpha-sig.figma.com/img/abbf/b433/0b725789bcee3b88e84a74c66b855212?Expires=1724025600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=olSNsCk1jJMUpPwQJdvhGQnH8~qCKpd3EBJAVBPO3nOXNb4T9iXWOCjxXhEEMuHV8X-tR2Zopy-nb798bkheH~VrSL-Vsa3AdpluPha-OWFCPhlbpQ-wSWYBwqNLHEVedsclqyBEDy5ylQsMsgGQsrqd41RAJB-fWi0nXHLXS82HnZEq4xN-0uracOjVemm2Wg0lvOsVaxVyFuCuFOXGht9gQV7l7PwxLzOwcVodwv2WrdQ2n3KY9t3p5I9BXWXvxFSa3nAZnuGIw7ZaTZUqe~u42t22K83B0QzFi-OftHXt8lzbHPdS9rz1nkOngXRWNC~spdmsl5n9qu8kDfgveQ__")
            .into(binding.imageMovie)
        binding.scrollTrendingMoviesMain.layoutManager = CenterZoomLayoutManager(requireContext())
        val adapter = AdapterPopularHome()
        binding.scrollTrendingMoviesMain.adapter = adapter
        adapter.submitList(listOf("1","2","4","8"))
    }

}