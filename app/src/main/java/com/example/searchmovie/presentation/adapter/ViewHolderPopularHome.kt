package com.example.searchmovie.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchmovie.databinding.ShowPopularMovieMainBinding

class ViewHolderPopularHome(private val binding : ShowPopularMovieMainBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        var name = item
        if (item.length > 10) {
           name = item.dropLast(item.length - 10).plus("...")
        }
        if(item != name){
            binding.textNameCardMovie.setOnClickListener {
                binding.textNameCardMovie.text = item
            }
        }
        binding.textNameCardMovie.text = name
        Glide.with(binding.root)
            .load("https://s3-alpha-sig.figma.com/img/abbf/b433/0b725789bcee3b88e84a74c66b855212?Expires=1724025600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=olSNsCk1jJMUpPwQJdvhGQnH8~qCKpd3EBJAVBPO3nOXNb4T9iXWOCjxXhEEMuHV8X-tR2Zopy-nb798bkheH~VrSL-Vsa3AdpluPha-OWFCPhlbpQ-wSWYBwqNLHEVedsclqyBEDy5ylQsMsgGQsrqd41RAJB-fWi0nXHLXS82HnZEq4xN-0uracOjVemm2Wg0lvOsVaxVyFuCuFOXGht9gQV7l7PwxLzOwcVodwv2WrdQ2n3KY9t3p5I9BXWXvxFSa3nAZnuGIw7ZaTZUqe~u42t22K83B0QzFi-OftHXt8lzbHPdS9rz1nkOngXRWNC~spdmsl5n9qu8kDfgveQ__")
            .into(binding.imageTrending)
    }
}