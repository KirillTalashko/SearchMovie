package com.example.searchmovie.presentation.cardMovie

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.common.model.ValueHolderView
import com.example.common.utils.BaseFragment
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.extension.loadPhoto
import com.example.searchmovie.core.extension.showToast
import com.example.searchmovie.databinding.FragmentCardMovieBinding
import com.example.searchmovie.presentation.cardMovie.adapter.AdapterRelatedMovie
import com.example.searchmovie.presentation.cardMovie.viewModel.CardMovieFragmentStateRelatedMovies
import com.example.searchmovie.presentation.cardMovie.viewModel.ViewModelCardMovie
import com.example.searchmovie.presentation.customView.InfoMovie
import javax.inject.Inject

class CardMovieFragment :
    BaseFragment<FragmentCardMovieBinding>(FragmentCardMovieBinding::inflate) {

    private val argsMovie: CardMovieFragmentArgs by navArgs()

    private val adapterRelatedMovie = AdapterRelatedMovie()

    private val inject by lazy(LazyThreadSafetyMode.NONE) {
        (requireContext().applicationContext as SearchMovieApp).appComponent.inject(this)
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: ViewModelCardMovie by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(
            this,
            factory
        )[ViewModelCardMovie::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        interactionWithView()
    }

    private fun initRecyclerView() {
        viewModel.getListMovieByGenre(argsMovie.infoMovie)
        binding.rvScrollSimilarMovie.adapter = adapterRelatedMovie
        viewModel.stateListMovieByGenre.observe(viewLifecycleOwner) {
            when (it) {
                is CardMovieFragmentStateRelatedMovies.Error -> {
                    requireContext().showToast(it.error)
                }

                CardMovieFragmentStateRelatedMovies.LoadingRelatedMovies -> {
                    Unit
                }

                is CardMovieFragmentStateRelatedMovies.SuccessRelatedMovies -> {
                    val currentList = adapterRelatedMovie.currentList
                    adapterRelatedMovie.submitList(currentList.plus(it.listMovie))
                }
            }
        }
        binding.rvScrollSimilarMovie.addOnScrollListener(
            object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager =
                        binding.rvScrollSimilarMovie.layoutManager as LinearLayoutManager
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    if (!viewModel.getIsLoading() && lastVisibleItem == totalItemCount - 3) {
                        viewModel.getListMovieByGenre(argsMovie.infoMovie)
                    }
                }
            }
        )
    }

    private fun interactionWithView() {
        binding.imageViewPosterMovie.loadPhoto(argsMovie.infoMovie.poster?.url ?: "")
        setCharacteristics()
        textExpander()
    }

    private fun textExpander() {
        viewModel.trackingReadMore.observe(viewLifecycleOwner) {
            binding.customViewInfoMovie.setExpandableText(
                fullText = argsMovie.infoMovie.description ?: getString(R.string.no_description),
                isExpanded = it,
                textColor = ContextCompat.getColor(requireContext(), R.color.black),
                isUnderline = it,
                onExpand = { viewModel.onReadMoreClicked() },
                onCollapse = { viewModel.onLessMoreClicked() }
            )
        }
    }

    private fun setCharacteristics() {
        binding.apply {
            customViewInfoMovie.setDataInfoMovie(
                name = argsMovie.infoMovie.name ?: getString(R.string.no_name),
                year = argsMovie.infoMovie.year.toString(),
                genres = argsMovie.infoMovie.genres ?: emptyList()
            )

            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.TIME,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_time
                    ),
                    firstText = argsMovie.infoMovie.duration.toString(),
                    secondText = getString(R.string.minutes)
                )
            )

            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.RATING_IMDB,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_star_gray
                    ),
                    firstText = argsMovie.infoMovie.rating.imd.toString(),
                    secondText = getString(R.string.text_rating_imdb)
                )
            )
            customViewInfoMovie.setDataCustomView(
                display = InfoMovie.DisplayOptionsCustomView.RATING_KP,
                owner = ValueHolderView(
                    drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.image_star_gray
                    ),
                    firstText = argsMovie.infoMovie.rating.kp.toString(),
                    secondText = getString(R.string.text_rating_kp)
                )
            )
        }
    }
}


