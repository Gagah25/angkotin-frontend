package com.example.angkotin.ui


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.angkotin.databinding.MapsLokasiBinding
import com.example.angkotin.fragment.fragmentMap.MapFragment


class MapsActivity: AppCompatActivity(){
    private lateinit var binding: MapsLokasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapsLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        var name = intent.getStringExtra(MapFragment.EXTRA_NAME)

        Log.d("MapsName", name.toString())
    }

    interface Communicator {
        fun passDataCom(editTextInput: String)
    }
}


