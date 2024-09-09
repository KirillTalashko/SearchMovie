package com.example.searchmovie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.FragmentHomeBinding
import com.example.searchmovie.extension.showToast
import com.example.searchmovie.presentation.adapter.AdapterPopularHome
import com.example.searchmovie.presentation.customView.CenterZoomLayoutManager
import com.example.searchmovie.presentation.viewModel.StatusRequest
import com.example.searchmovie.presentation.viewModel.ViewModelFactory
import com.example.searchmovie.presentation.viewModel.ViewModelRandomMovie
import com.example.searchmovie.utils.ImageHelper
import com.example.searchmovie.utils.SearchMovieApp
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val adapterMovieMain = AdapterPopularHome()
    private val currentListEmpty: Boolean
        get() = adapterMovieMain.currentList.isEmpty()


    private val viewModel: ViewModelRandomMovie by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this,
            TODO("не смог коректно сделать фабрику и зависимости")
        )[ViewModelRandomMovie::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

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
        initRecyclerView()
        observerViewModel()
        interactionWithView()
    }

    private fun interactionWithView() {
        binding.cardViewMovie.restartStateButton.setOnClickListener {
            viewModel.getRandomMovie()
        }
    }

    private fun initRecyclerView() {
        binding.scrollTrendingMoviesMain.layoutManager = CenterZoomLayoutManager(requireContext())
        binding.scrollTrendingMoviesMain.adapter = adapterMovieMain
        binding.scrollTrendingMoviesMain.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager =
                    binding.scrollTrendingMoviesMain.layoutManager as CenterZoomLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!viewModel.getIsLoading() && lastVisibleItem == totalItemCount - 3) {
                    viewModel.getListMovie()
                }
            }
        })
    }

    private fun observerViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is StatusRequest.Error -> {
                    requireContext().showToast(it.error)
                    binding.cardViewMovie.restartStateButton.visibility = View.VISIBLE
                }

                StatusRequest.LoadingListMovie -> {
                    if (currentListEmpty) {
                        binding.shimmerScrollListMovie.startShimmer()
                        binding.shimmerScrollListMovie.visibility = View.VISIBLE
                        binding.scrollTrendingMoviesMain.visibility = View.GONE
                    }
                }

                StatusRequest.LoadingMovie -> {
                    binding.apply {
                        cardViewMovie.restartStateButton.visibility = View.GONE
                        shimmerPlayCard.startShimmer()
                        shimmerPlayCard.visibility = View.VISIBLE
                    }
                }

                is StatusRequest.SuccessListMovie -> {
                    if (currentListEmpty) {
                        binding.apply {
                            shimmerScrollListMovie.stopShimmer()
                            shimmerScrollListMovie.visibility = View.GONE
                            scrollTrendingMoviesMain.visibility = View.VISIBLE
                        }
                    }
                    val currentList = adapterMovieMain.currentList
                    adapterMovieMain.submitList(currentList.plus(it.listMovie))
                }

                is StatusRequest.SuccessMovie -> {
                    binding.apply {
                        shimmerPlayCard.stopShimmer()
                        shimmerPlayCard.visibility = View.GONE
                        cardViewMovie.cardMovieMain.visibility = View.VISIBLE
                        cardViewMovie.playCard.getTextNameView().text =
                            it.movie.name ?: "Нет названия"
                    }
                    ImageHelper().getPhoto(it.movie.poster?.url, binding.cardViewMovie.imageMovie)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
