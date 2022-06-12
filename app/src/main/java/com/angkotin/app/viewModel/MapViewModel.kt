package com.angkotin.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angkotin.app.data.DriverData
import com.angkotin.app.data.DriverLocation
import com.angkotin.app.data.DriverResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class MapViewModel: ViewModel() {
    private val dataLoc = MutableLiveData<ArrayList<DriverResponse>>()
    private val location: LiveData<ArrayList<DriverResponse>> = dataLoc

    val angkotDb = FirebaseFirestore.getInstance()
    val colRef = angkotDb.collection(   "users")

    fun setDataFirebase(){
        val driverList = ArrayList<DriverResponse>()
        colRef.addSnapshotListener { value, error ->
            error?.let { doc ->
                return@addSnapshotListener
            }
            value?.let {
                for (doc in it) {
                    if (doc != null) {
                        val angkot = doc.toObject(DriverResponse::class.java)
                        val latitude = angkot.location?.latitude
                        val longitude = angkot.location?.longitude
                        val angkotNumber = angkot.driverMeta?.angkotNumber
                        val isActive = angkot.driverMeta?.isActive
                        val angkotLabel = angkot.driverMeta?.angkotLabel
                        driverList.add(
                            DriverResponse(
                                driverMeta = DriverData(
                                    angkotNumber = angkotNumber,
                                    isActive = isActive,
                                    angkotLabel = angkotLabel
                                ),
                                location = DriverLocation(
                                    latitude = latitude,
                                    longitude = longitude
                                ),
                                name = angkot.name,
                                nik = angkot.nik,
                                phoneNumber = angkot.phoneNumber,
                                role = angkot.role
                            )
                        )
                    }
                    dataLoc.value = driverList
                }
            }
        }
    }

    fun getDataFirebase():LiveData<ArrayList<DriverResponse>>{
        return location
    }
}