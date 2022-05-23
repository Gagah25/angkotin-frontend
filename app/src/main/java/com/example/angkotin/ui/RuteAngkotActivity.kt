package com.example.angkotin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkotin.databinding.RuteAngkotMalangBinding

class RuteAngkotActivity: AppCompatActivity() {
    private lateinit var binding: RuteAngkotMalangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RuteAngkotMalangBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}