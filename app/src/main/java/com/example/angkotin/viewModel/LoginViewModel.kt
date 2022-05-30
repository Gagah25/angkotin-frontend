package com.example.angkotin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.angkotin.ApiConfig
import com.example.angkotin.data.DataLogin
import com.example.angkotin.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    val userLogin = MutableLiveData<LoginResponse>()
    val userDataLogin = MutableLiveData<DataLogin>()
    private lateinit var loginResponse: LoginResponse

    fun setLogin(phoneNumber: String, password: String){
        loginResponse = LoginResponse(data = null, null, phoneNumber.toString(), password.toString())
        val client = ApiConfig.getApiService().postLogin(loginResponse)
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

    fun setDataLogin(phoneNumber: String, password: String){
        loginResponse = LoginResponse(data = null, null, phoneNumber.toString(), password.toString())
        val client = ApiConfig.getApiService().postLogin(loginResponse)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val result = response.body()?.data
                    if (result != null){
                        userDataLogin.postValue(result)
                    }
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
    fun getDataLogin(): LiveData<DataLogin>{
        return userDataLogin
    }
}

