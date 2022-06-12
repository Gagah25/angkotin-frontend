package com.angkotin.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angkotin.app.ApiConfig
import com.angkotin.app.BuildConfig.MAPS_API_KEY
import com.angkotin.app.data.FindPlacesResponse
import com.angkotin.app.data.PredictionsItem
import retrofit2.Call
import retrofit2.Response

class SearchPlacesViewModel: ViewModel(){
    val listUser = MutableLiveData<ArrayList<PredictionsItem?>>()
    val idPlace = MutableLiveData<String?>()

    fun setFindPlace(query: String){
        val client = ApiConfig.getApiFindPlacesService().getFindPlaces(query, "geocode", "${MAPS_API_KEY}")
        client.enqueue(object : retrofit2.Callback<FindPlacesResponse>{
            override fun onResponse(
                call: Call<FindPlacesResponse>,
                response: Response<FindPlacesResponse>,
            ) {
                if (response.isSuccessful){
                    listUser.postValue(response.body()?.predictions!!)
                    val predictions: ArrayList<PredictionsItem?>? = response.body()?.predictions

                    for (i in predictions?.indices!!){
                        var placeId: String? = predictions.get(i)?.placeId
                        idPlace.postValue(placeId)
                    }
                }
            }

            override fun onFailure(call: Call<FindPlacesResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getFindPlace(): LiveData<ArrayList<PredictionsItem?>>{
        return listUser
    }

    fun getPlaceId(): LiveData<String?>{
        return idPlace
    }
}