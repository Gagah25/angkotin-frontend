package com.example.angkotin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.angkotin.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnCariAngkot.setOnClickListener{ moveToCariAngkot() }
            btnRuteAngkot.setOnClickListener { moveToRuteAngkot() }
            btnBantuan.setOnClickListener { moveToBantuan() }
        }
    }

    private fun moveToCariAngkot(){
        val intent = Intent(this@HomeActivity, MapsActivity::class.java)
        startActivity(intent)
    }

    private fun moveToRuteAngkot(){
        val intent = Intent(this@HomeActivity, RuteAngkotActivity::class.java)
        startActivity(intent)
    }

    private fun moveToBantuan(){
        val intent = Intent(this@HomeActivity, BantuanActivity::class.java)
        startActivity(intent)
    }
}