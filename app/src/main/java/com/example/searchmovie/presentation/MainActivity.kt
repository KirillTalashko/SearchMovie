package com.example.searchmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.common.extension.log
import com.example.common.extension.showToast
import com.example.searchmovie.R
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.databinding.ActivityMainBinding
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
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomsNavMenu.setupWithNavController((supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment).navController)
        errorManager = ErrorManager(this)
        displayErrors()
    }

    private fun displayErrors() {
        lifecycleScope.launch {
            errorManager.errorMessage.collect { massage ->
                this@MainActivity.showToast(massage)
                massage.log()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}