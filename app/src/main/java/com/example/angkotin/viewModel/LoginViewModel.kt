package com.example.angkotin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.angkotin.ApiConfig
import com.example.angkotin.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    val userLogin = MutableLiveData<LoginResponse>()
    private lateinit var loginResponse: LoginResponse

    fun setLogin(phoneNumber: String, password: String){
        loginResponse = LoginResponse(error = null, phoneNumber.toString(), password.toString())
        val client = ApiConfig.getApiService().postLogin(LoginResponse())
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    userLogin.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getLogin(): LiveData<LoginResponse> {
        return userLogin
    }
}

