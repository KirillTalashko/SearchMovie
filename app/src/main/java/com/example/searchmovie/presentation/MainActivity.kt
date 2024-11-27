package com.example.searchmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.common.utils.IntervalTimer
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.NetworkCheckerWorker
import com.example.searchmovie.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
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
        checkNetworkAccess()
    }

    private fun displayErrors() {
        lifecycleScope.launch {
            errorManager.errorMessage.collect { massage ->
                binding.customViewError.showError(massage)
            }
        }
    }

    private fun checkNetworkAccess() {
        val networkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<NetworkCheckerWorker>()
            .setInitialDelay(IntervalTimer.setIntervalTime(5), TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this@MainActivity).enqueue(networkRequest)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}