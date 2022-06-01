package com.example.angkotin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.angkotin.ApiConfig
import com.example.angkotin.data.Data
import com.example.angkotin.data.Location
import com.example.angkotin.data.PassengerResponse
import retrofit2.Call
import retrofit2.Response

class AccountViewModel: ViewModel() {
    val userData = MutableLiveData<Data>()

    fun setData(token: String, id: String){
        val client = ApiConfig.getApiService().getPassenger(token, id)
        client.enqueue(object : retrofit2.Callback<PassengerResponse>{
            override fun onResponse(
                call: Call<PassengerResponse>,
                response: Response<PassengerResponse>) {
                if (response.isSuccessful){
                    val result = response.body()?.data
                    if (result != null){
                        userData.postValue(result!!)
                    }
                }
            }

            override fun onFailure(call: Call<PassengerResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun setDataLocation(token: String, id: String, data: Location){
        val client = ApiConfig.getApiService().updateLocationUser(token, id, data)
        client.enqueue(object : retrofit2.Callback<PassengerResponse>{
            override fun onResponse(
                call: Call<PassengerResponse>,
                response: Response<PassengerResponse>,
            ) {
                val result = response.body()
            }

            override fun onFailure(call: Call<PassengerResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getData():LiveData<Data>{
        return userData
    }
}