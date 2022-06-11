package com.angkotin.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.angkotin.app.databinding.BantuanBinding

class BantuanActivity: AppCompatActivity() {
    private lateinit var binding: BantuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BantuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}