package com.example.searchmovie.core.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.common.model.DataDisplayMode
import com.example.searchmovie.R

class ErrorDialog(
    private val dataDisplayMode: DataDisplayMode,
    private val action: (() -> Unit)? = null
) : DialogFragment() {

    companion object {
        const val TAG_LOCAL_DATA = "ShowMovieFromDatabase"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(dataDisplayMode.title)
            .setMessage(dataDisplayMode.description)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.positive_option)) { dialog, _ ->
                action?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.negative_option)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
}