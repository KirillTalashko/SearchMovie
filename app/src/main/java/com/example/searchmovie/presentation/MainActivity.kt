package com.example.searchmovie.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchmovie.R
import com.example.searchmovie.core.extension.log
import com.example.searchmovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate Activity".log()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomsNavMenu.setupWithNavController((supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment).navController)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        "Config Activity".log()
    }

    override fun onDestroy() {
        "onDestroy Activity".log()
        super.onDestroy()
        _binding = null
    }
}