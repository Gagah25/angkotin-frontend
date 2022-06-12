package com.angkotin.app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _nameDriver = MutableLiveData<String>()
    val nameDriver = _nameDriver
    private val _placeId = MutableLiveData<String>()
    val placeId = _placeId

    fun setNameDriver(nameDriver: String){
        _nameDriver.value = nameDriver
    }

    fun setPlaceId(placeId: String){
        _placeId.value = placeId
    }

    fun getNameDriver(): LiveData<String>{
        return nameDriver
    }

    fun getPlaceId(): LiveData<String>{
        return placeId
    }
}