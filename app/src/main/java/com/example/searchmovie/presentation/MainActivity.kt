package com.example.searchmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchmovie.R
import com.example.searchmovie.core.extension.logActivity
import com.example.searchmovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        "onCreate".logActivity()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomsNavMenu.setupWithNavController((supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment).navController)
    }

    override fun onStart() {
        super.onStart()
        "onStart".logActivity()
    }

    override fun onResume() {
        super.onResume()
        "onResume".logActivity()
    }

    override fun onStop() {
        super.onStop()
        "onStop".logActivity()
    }

    override fun onRestart() {
        super.onRestart()
        "onRestart".logActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy".logActivity()
        _binding = null
    }
}