package com.example.searchmovie.core.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.searchmovie.R

class ErrorDialog(
    private val isMovieOrMovies: Boolean? = null,
    private val getMovie: (() -> Unit)? = null,
    private val getMovies: (() -> Unit)? = null
) : DialogFragment() {

    companion object {
        const val TAG = "ShowMovieFromDatabase"
    }

    private var isLocalData = false

    fun getIsLocalData() = isLocalData

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.no_internet))
            .setMessage(
                if (isMovieOrMovies == true) {
                    getString(R.string.show_random_movie)
                } else {
                    getString(R.string.show_selection_of_movies)
                }
            )
            .setCancelable(true)
            .setPositiveButton(getString(R.string.positive_option)) { dialog, _ ->
                isLocalData = true
                if (isMovieOrMovies == true) {
                    getMovie
                } else {
                    getMovies
                }
                isLocalData = false
                dialog.cancel()
            }
            .setNegativeButton(getString(R.string.negative_option)) { dialog, _ ->
                isLocalData = false
                dialog.cancel()
            }
            .create()
}