package com.angkotin.app.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.angkotin.app.data.UserPreference
import com.angkotin.app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedPref = UserPreference(this)
        val username = sharedPref.fetchUserName()

        binding.apply {
            binding.tvGreet.text = "Hello " +  username
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
        val intent = Intent(this@HomeActivity, RouteAngkotActivity::class.java)
        startActivity(intent)
    }

    private fun moveToBantuan(){
        val intent = Intent(this@HomeActivity, BantuanActivity::class.java)
        startActivity(intent)
    }
}