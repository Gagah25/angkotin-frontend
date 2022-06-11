package com.angkotin.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.angkotin.app.R
import com.angkotin.app.data.UserPreference
import com.angkotin.app.databinding.ActivityLoginBinding
import com.angkotin.app.viewModel.AccountViewModel
import com.angkotin.app.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var accountViewModel: AccountViewModel
    private lateinit var nomorHpL: String
    private lateinit var passL: String
    private lateinit var err: String
    private lateinit var sharedPref: UserPreference
    private lateinit var tkn: String
    private lateinit var idUser: String
    private lateinit var namePassenger: String
    private lateinit var numberPhonePassenger: String
    private lateinit var token: String
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = UserPreference(this)

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
        loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(LoginViewModel::class.java)
        accountViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AccountViewModel::class.java)

        binding.apply {
            nomorHpL = edtNomorHp.text.toString()
            passL = edtPassword.text.toString()
        }

        loginViewModel.setLogin(nomorHpL, passL)
        loginViewModel.setDataLogin(nomorHpL, passL)

        loginViewModel.getDataLogin().observe(this, {
            if (it != null) {
                with(it) {
                    tkn = token.toString()
                    idUser = id.toString()
                    Log.d("Token", tkn)
                    if (id != null) {
                        Log.d("ID", id)
                    }
                }
                sharedPref.saveToken(tkn)
                sharedPref.saveID(idUser)
            }
        })

        loginViewModel.getLogin().observe(this, {
            if (it != null) {
                with(it) {
                    err = success.toString()
                    Log.d("Error message", err)
                }
            }
            if (err == "true") {
                Toast.makeText(this, "Anda berhasil Masuk\nTunggu Sebentar", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.Main).launch {
                    getData()
                    delay(4000L)
                    sharedPref.setPref(UserPreference.PREF_IS_LOGIN, true)
                    val mIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(mIntent)
                }
            }else {
                    Toast.makeText(this, "Gagal Masuk", Toast.LENGTH_LONG).show()
                }
        })

    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getBoolean(UserPreference.PREF_IS_LOGIN) == true) {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            finish()
        }
    }

    private fun getData(){
        token = "Bearer ${sharedPref.fetchToken()}"
        user = "${sharedPref.fetchID()}"
        accountViewModel.setData(token, user)

        accountViewModel.getData().observe(this@LoginActivity,  {
            if (it != null){
                namePassenger = it.name.toString()
                numberPhonePassenger = it.phoneNumber.toString()
            }
            sharedPref.saveUserName(namePassenger)
            sharedPref.savePhoneNumber(numberPhonePassenger)
        })
    }
}
