package com.angkotin.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.angkotin.app.data.UserPreference
import com.angkotin.app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)

        binding.apply {
            binding.tvGreet.text = "Halo ${sharedPref.fetchUserName()}"
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