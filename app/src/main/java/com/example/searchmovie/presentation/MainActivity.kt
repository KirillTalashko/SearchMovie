package com.example.searchmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.common.utils.Core
import com.example.searchmovie.R
import com.example.searchmovie.SearchMovieApp
import com.example.searchmovie.core.utils.ErrorManager
import com.example.searchmovie.core.utils.NetworkCheckerWorker
import com.example.searchmovie.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!


    @Inject
    lateinit var errorManager: ErrorManager

    @Inject
    lateinit var core: Core


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
        lifecycleScope.launch {
            core.networkChecker.collect {
                if (it) {
                    binding.customViewError.showError(resources.getString(R.string.connect_internet))
                } else {
                    binding.customViewError.showError(resources.getString(R.string.no_internet))
                }
            }
        }
    }


    private fun checkNetworkAccess() {
        val networkRequest: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<NetworkCheckerWorker>().build()

        WorkManager.getInstance(this@MainActivity)
            .enqueueUniqueWork("NetworkChecker", ExistingWorkPolicy.REPLACE, networkRequest)

    }

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(this@MainActivity).cancelUniqueWork("NetworkChecker")
        _binding = null
    }
}