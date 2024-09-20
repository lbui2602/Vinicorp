package com.example.freshervnc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.freshervnc.databinding.ActivityMainBinding
import com.example.freshervnc.luckychoice.LuckyChoiceActivity
import com.example.freshervnc.restaurant.ui.RestaurantActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLuckyChoice.setOnClickListener {
            startActivity(Intent(this@MainActivity, LuckyChoiceActivity::class.java))
        }
        binding.btnRestaurant.setOnClickListener {
            startActivity(Intent(this@MainActivity, RestaurantActivity::class.java))
        }
    }
}