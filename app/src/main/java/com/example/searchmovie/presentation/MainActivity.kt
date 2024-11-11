package com.example.searchmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchmovie.R
import com.example.searchmovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomsNavMenu.setupWithNavController((supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment).navController)
        /*lifecycleScope.launch {
            ErrorManager.errorMassage.collect {
                this@MainActivity.showToast(it)
            }
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}