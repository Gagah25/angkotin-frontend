package com.example.angkotin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.angkotin.data.UserPreference
import com.example.angkotin.databinding.NavigasiBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingActivity: AppCompatActivity() {
    private lateinit var binding: NavigasiBinding
    private lateinit var sharedPref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)
        val name = "${sharedPref.fetchUserName()}"
        val phoneNUmber = "${sharedPref.fetchPhoneNumber()}"
        Log.d("Name", name)
        Log.d("PhoneNumber", phoneNUmber)

        binding.apply {
            userName.text = name
            userTelepon.text = phoneNUmber
            buttonClose.setOnClickListener{ moveToMaps() }
            keluar.setOnClickListener { logOut() }
            ruteAngkot.setOnClickListener { moveToRuote() }
            beranda.setOnClickListener { moveToHome() }
        }
    }

    private fun moveToMaps(){
        val intent = Intent(this@SettingActivity, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun logOut(){
        Toast.makeText(this, "Berhasil Log Out", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000L)
            sharedPref.delete()
            startActivity(Intent(this@SettingActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun moveToHome(){
        val intent = Intent(this@SettingActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToRuote(){
        val intent = Intent(this@SettingActivity, RouteAngkotActivity::class.java)
        startActivity(intent)
    }
}