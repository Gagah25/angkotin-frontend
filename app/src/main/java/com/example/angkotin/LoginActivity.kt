package com.example.angkotin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.angkotin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener(this)
        binding.linkDaftar.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_masuk -> {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            R.id.link_daftar -> {
                val intent = Intent(this@LoginActivity, DaftarActivity::class.java)
                startActivity(intent)
            }
        }
    }


}

