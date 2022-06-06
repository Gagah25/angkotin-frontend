package com.example.angkotin.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkotin.databinding.MapsLokasiBinding


class MapsActivity: AppCompatActivity(){
    private lateinit var binding: MapsLokasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapsLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}


