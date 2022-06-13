package com.angkotin.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angkotin.app.ApiConfig
import com.angkotin.app.BuildConfig.MAPS_API_KEY
import com.angkotin.app.data.GeocodingResponse
import com.angkotin.app.data.Location
import com.angkotin.app.data.ResultsItem
import retrofit2.Call
import retrofit2.Response

class GeocodingViewModel: ViewModel() {
    val geocodeData = MutableLiveData<Location>()

    fun setGeocode(placeId: String){
        val client = ApiConfig.getApiGeocoding().getGeocoding(placeId, "${MAPS_API_KEY}")
        client.enqueue(object : retrofit2.Callback<GeocodingResponse>{
            override fun onResponse(
                call: Call<GeocodingResponse>,
                response: Response<GeocodingResponse>,
            ) {
                if (response.isSuccessful){
                    val result: List<ResultsItem?>? = response.body()?.results

                    for (i in result?.indices!!){
                        var geometry: Location? = result.get(i)?.geometry?.location
                        geocodeData.postValue(geometry!!)
                    }

                }
            }

            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getGeocode(): LiveData<Location>{
        return geocodeData
    }
}