package com.angkotin.app.ui


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.angkotin.app.databinding.MapsLokasiBinding
import com.angkotin.app.fragment.fragmentMap.MapFragment


class MapsActivity: AppCompatActivity(){
    private lateinit var binding: MapsLokasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapsLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val intent = getIntent()
        var name = intent.getStringExtra(MapFragment.EXTRA_NAME)

        Log.d("MapsName", name.toString())
    }
}


