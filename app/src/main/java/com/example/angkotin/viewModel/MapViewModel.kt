package com.example.angkotin.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.angkotin.data.DataLocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore

class MapViewModel: ViewModel() {
    private val dataLoc = MutableLiveData<ArrayList<Double>>()
    private val location: LiveData<ArrayList<Double>> = dataLoc
    val angkotCollectionRef = FirebaseFirestore.getInstance().collection("users")

    fun setDataFirebase(){
        var lat: Any?
        var lon: Any?
        var tempLat = ArrayList<Double>()
        var tempLon = ArrayList<Double>()
        angkotCollectionRef.addSnapshotListener{ querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let { document ->
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val sb = StringBuilder()
                for (document in it){
                    val angkot = document.data.get("location") as Map<*,*>
                    lat = angkot["latitude"]
                    if (lat is Any){
                        tempLat = arrayListOf(lat.toString().toDouble())
                    }
                    lon = angkot["longitude"]
                    if (lon is Any){
                        tempLon = arrayListOf(lon.toString().toDouble())
                    }
                    Log.d("Firebase Lat", lat.toString())
                    Log.d("Firebase Lon", lon.toString())
                }
                for (i in tempLat.indices){

                }

            }
        }
    }

    fun getDataFirebase():LiveData<ArrayList<Double>>{
        return location
    }
}