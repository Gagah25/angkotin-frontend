package com.angkotin.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.angkotin.app.databinding.ActivitySelesaiBinding
import com.angkotin.app.ui.HomeActivity
import com.angkotin.app.ui.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SelesaiActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelesaiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelesaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

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