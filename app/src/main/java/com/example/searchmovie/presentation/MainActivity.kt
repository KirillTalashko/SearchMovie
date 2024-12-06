package com.example.searchmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var errorManager: ErrorManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.applicationContext as SearchMovieApp).appComponent.inject(this)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomsNavMenu.setupWithNavController((supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment).navController)
        displayErrors()
    }

    private fun displayErrors() {
        lifecycleScope.launch {
            errorManager.errorMessage.collect { massage ->
                binding.customViewError.showError(massage)
            }
        }
        lifecycleScope.launch(Dispatchers.Main) {
            errorManager.errorMessageDialog.collect { dialogInfo ->
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle(dialogInfo.title)
                    .setMessage(dialogInfo.description)
                    .setCancelable(true)
                    .setPositiveButton(
                        dialogInfo.nameActionNegative
                            ?: this@MainActivity.getString(R.string.positive_option)
                    ) { dialog, _ ->
                        dialogInfo.actionPositiveFirst?.invoke()
                        dialogInfo.actionPositiveSecond?.invoke()
                        dialog.dismiss()
                    }
                    .setNegativeButton(
                        dialogInfo.nameActionNegative
                            ?: this@MainActivity.getString(R.string.negative_option)
                    ) { dialog, _ ->
                        dialogInfo.actionNegative?.invoke()
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}