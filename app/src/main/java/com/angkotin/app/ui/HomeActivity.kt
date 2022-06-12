package com.angkotin.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.angkotin.app.data.UserPreference
import com.angkotin.app.databinding.ActivityHomeBinding
import com.angkotin.app.viewModel.EstimationViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPref: UserPreference
    private lateinit var viewModel: EstimationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(EstimationViewModel::class.java)

        viewModel.setEstimation()
        viewModel.getEstimations().observe(this,{
            Log.d("Gatel", it.toString())
            binding.cobaEstimasi.text = it?.toInt().toString()
        })

        binding.apply {

            tvGreet.text = "Halo ${sharedPref.fetchUserName()}"
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