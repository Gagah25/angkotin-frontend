package com.angkotin.app.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.angkotin.app.databinding.ActivitySelesaiBinding

class SelesaiActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelesaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelesaiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.btnKembali.setOnClickListener {
            val intent = Intent(this@SelesaiActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.buttonKeluar.setOnClickListener {
            Toast.makeText(this, "Berhasil Keluar", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SelesaiActivity, LoginActivity::class.java))
            finish()
        }
    }
}