package com.example.angkotin.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkotin.databinding.NavigasiBinding

class SettingActivity: AppCompatActivity() {
    private lateinit var binding: NavigasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonClose.setOnClickListener{ moveToMaps() }
        }
    }

    private fun moveToMaps(){
        val intent = Intent(this@SettingActivity, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

}