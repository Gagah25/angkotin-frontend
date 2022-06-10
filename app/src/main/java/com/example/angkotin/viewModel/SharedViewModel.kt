package com.example.angkotin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _nameDriver = MutableLiveData<String>()
    val nameDriver = _nameDriver

    fun setNameDriver(nameDriver: String){
        _nameDriver.value = nameDriver
    }

    fun getNameDriver(): LiveData<String>{
        return nameDriver
    }
}