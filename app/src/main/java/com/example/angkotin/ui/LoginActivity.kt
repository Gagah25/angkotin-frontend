package com.example.angkotin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.angkotin.R
import com.example.angkotin.databinding.ActivityLoginBinding
import com.example.angkotin.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var nomorHpL: String
    private lateinit var passL: String
    private lateinit var err: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener{
            login()
        }
        binding.linkDaftar.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.link_daftar -> {
                val intent = Intent(this@LoginActivity, DaftarActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun login() {
        loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            LoginViewModel::class.java)

        binding.apply {
            nomorHpL = edtNomorHp.text.toString()
            passL = edtPassword.text.toString()
        }

        loginViewModel.setLogin(nomorHpL, passL)
        loginViewModel.getLogin().observe(this, {
            if (it != null) {
                with(it) {
                    err = error.toString()
                    Log.d("Error message", err)
                }
            }
            if (err == "true") {
                Toast.makeText(this, "Anda berhasil Masuk\nTunggu Sebentar", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000L)
                    val mIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(mIntent)
                }
            }else {
                    Toast.makeText(this, "Gagal Masuk", Toast.LENGTH_LONG).show()
                }
        })
    }
}
