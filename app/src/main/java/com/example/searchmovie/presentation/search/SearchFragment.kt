package com.example.searchmovie.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.domain.state.MovieCategorySearchFragmentState
import com.example.domain.state.MoviesByCategoriesSearchFragmentState
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.databinding.FragmentSearchBinding
import com.example.searchmovie.presentation.modelMovie.MovieUi
import com.example.searchmovie.presentation.search.adatper.MovieCardByCategoryAdapter
import com.example.searchmovie.presentation.search.adatper.MovieCategoryAdapter
import com.example.searchmovie.presentation.search.viewModel.MovieSearchViewModel
import com.example.searchmovie.presentation.utils.OnClickGetModel
import com.example.searchmovie.presentation.utils.extension.toListCategoryUi
import com.example.searchmovie.presentation.utils.extension.toListMovieUi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class SearchFragment : BottomSheetDialogFragment(), OnClickGetModel {

    private var _binding: FragmentSearchBinding? = null

    private val binding
        get() = _binding!!

    private lateinit var adapterTypeMovie: MovieCategoryAdapter
    private lateinit var adapterCardMovieByType: MovieCardByCategoryAdapter

    private var isCategory: String? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: MovieSearchViewModel by viewModels<MovieSearchViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observerViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()
        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { bottomSheet ->
                val behavior = BottomSheetBehavior.from(bottomSheet)
                val displayMetrics = resources.displayMetrics
                val percentageHeight = (displayMetrics.heightPixels * 0.9).toInt()
                bottomSheet.layoutParams.height = percentageHeight
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
    }

    private fun initRecyclerView() {
        adapterTypeMovie = MovieCategoryAdapter { category ->
            category.name?.let { viewModel.getMoviesByCategory(it) }
            isCategory = category.name
        }
        adapterCardMovieByType = MovieCardByCategoryAdapter(this)

        binding.recyclerViewType.adapter = adapterTypeMovie
        binding.recyclerViewMovieByType.adapter = adapterCardMovieByType

        binding.recyclerViewMovieByType.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        binding.recyclerViewMovieByType.layoutManager as GridLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!viewModel.getIsLoading() && lastVisibleItem >= totalItemCount - 3) {
                        if (isCategory != null) {
                            viewModel.getMoviesByCategory(isCategory!!)
                        }
                    }
                }
            }
        )
    }

    private fun observerViewModel() {
        viewModel.stateMovieCategory.observe(viewLifecycleOwner) { categoryState ->
            when (categoryState) {
                MovieCategorySearchFragmentState.Error -> Unit
                MovieCategorySearchFragmentState.LoadingMoviesSearch -> Unit
                is MovieCategorySearchFragmentState.SuccessMoviesSearch -> {

                    val currentList = adapterTypeMovie.currentList
                    adapterTypeMovie.submitList(currentList.plus(categoryState.categories.toListCategoryUi()))

                    if (currentList.isNotEmpty()) {
                        val firstType = currentList[0]
                        firstType.name?.let { type ->
                            viewModel.getMoviesByCategory(
                                type
                            )
                        }
                    }
                }
            }
        }
        viewModel.stateMoviesByCategory.observe(viewLifecycleOwner) { movieState ->
            when (movieState) {
                MoviesByCategoriesSearchFragmentState.Error -> Unit
                MoviesByCategoriesSearchFragmentState.LoadingMoviesSearch -> Unit
                is MoviesByCategoriesSearchFragmentState.SuccessMoviesSearch -> {
                    val currentList = adapterCardMovieByType.currentList
                    if (currentList.isNotEmpty() && movieState.update) {
                        adapterCardMovieByType.submitList(emptyList())
                        adapterCardMovieByType.submitList(movieState.movies.toListMovieUi())
                    } else adapterCardMovieByType.submitList(currentList.plus(movieState.movies.toListMovieUi()))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun getMovieModel(movie: MovieUi) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToCardMovieFragment(infoMovie = movie)
        )
    }

}
