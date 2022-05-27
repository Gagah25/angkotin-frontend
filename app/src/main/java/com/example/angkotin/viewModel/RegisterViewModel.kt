package com.example.angkotin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.angkotin.ApiConfig
import com.example.angkotin.data.RegisterResponse
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel: ViewModel() {
    val userDaftar = MutableLiveData<RegisterResponse>()
    private lateinit var registerResponse: RegisterResponse

    fun setRegister (name: String, nik: String, phoneNumber: String, password: String){
        registerResponse = RegisterResponse(error = null, name.toString(), nik.toString(), phoneNumber.toString(), password.toString())
        val client = ApiConfig.getApiService().getStringScalar(registerResponse)
        client.enqueue(object : retrofit2.Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    userDaftar.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getRegister(): LiveData<RegisterResponse> {
        return userDaftar
    }
}