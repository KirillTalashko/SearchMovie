package com.example.searchmovie.core.utils

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.common.model.DialogInfo
import com.example.searchmovie.R

class ErrorDialog(
    private val dialogInfo: DialogInfo
) : DialogFragment() {

    companion object {
        const val TAG_LOCAL_DATA = "ShowMovieFromDatabase"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(dialogInfo.title)
            .setMessage(dialogInfo.description)
            .setCancelable(true)
            .setPositiveButton(
                dialogInfo.nameActionNegative ?: getString(R.string.positive_option)
            ) { dialog, _ ->
                dialogInfo.actionPositiveFirst?.invoke()
                dialogInfo.actionPositiveSecond?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(
                dialogInfo.nameActionNegative ?: getString(R.string.negative_option)
            ) { dialog, _ ->
                dialogInfo.actionNegative?.invoke()
                dialog.dismiss()
            }
            .create()
}