package com.example.cinemo.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.cinemo.R
import com.example.cinemo.base.BaseActivity
import com.example.cinemo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun getNavController(): NavController {
        return findNavController(R.id.allCurrencyFragment)
    }
}