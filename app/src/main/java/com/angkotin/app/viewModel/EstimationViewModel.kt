package com.angkotin.app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angkotin.app.ApiConfig
import com.angkotin.app.data.DataML
import com.angkotin.app.data.MachineLearnigResponse
import retrofit2.Call
import retrofit2.Response


class EstimationViewModel: ViewModel() {
    val _estimation = MutableLiveData<Double?>()
    private lateinit var dataML: DataML

    fun setEstimation(){
        dataML = DataML("AG",120,1110,10,-7.9332112970817095,112.65815130110964)
        val client = ApiConfig.getApiMlService().getEstimation(dataML)
        client.enqueue(object : retrofit2.Callback<MachineLearnigResponse>{
            override fun onResponse(call: Call<MachineLearnigResponse>, response: Response<MachineLearnigResponse>) {
                Log.d("responseml", response.toString())
                if (response.isSuccessful){
                    _estimation.postValue(response.body()?.data?.predictions!![0][0])

                }
            }

            override fun onFailure(call: Call<MachineLearnigResponse>, t: Throwable) {
                TODO("Not yet implemented")
                Log.d("throw", t.message.toString())
            }

        })
    }
    fun getEstimations(): LiveData<Double?>{
        return _estimation
    }
}