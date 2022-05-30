package com.example.angkotin.data

import android.content.Context
import android.content.SharedPreferences

internal class UserPreference(context: Context) {
    private val sharedPref: SharedPreferences
    val putData: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        putData = sharedPref.edit()
    }

    fun setPref(key: String, value: Boolean){
        putData.putBoolean(key, value).apply()
    }

    fun saveUserName(username: String){
        putData.putString(PREF_USERNAME, username)
        putData.apply()
    }

    fun savePhoneNumber(phoneNumber: String){
        putData.putString(PREF_PHONE_NUMBER, phoneNumber)
        putData.apply()
    }

    fun saveID(id: String){
        putData.putString(PREF_ID, id)
        putData.apply()
    }

    fun saveToken(token: String){
        putData.putString(PREF_TOKEN, token)
        putData.apply()
    }

    fun fetchToken(): String?{
        return sharedPref.getString(PREF_TOKEN, null)
    }

    fun fetchID(): String?{
        return sharedPref.getString(PREF_ID, null)
    }

    fun fetchUserName(): String?{
        return sharedPref.getString(PREF_USERNAME, null)
    }

    fun fetchPhoneNumber(): String?{
        return sharedPref.getString(PREF_PHONE_NUMBER, null)
    }

    fun getBoolean(key: String): Boolean?{
        return sharedPref.getBoolean(key, false)
    }

    fun delete(){
        putData.clear().apply()
    }

    companion object {
        const val PREFS_NAME = "user_pref"
        const val PREF_IS_LOGIN = "Pref_Is_Login"
        const val PREF_USERNAME = "Pref_Username"
        const val PREF_TOKEN = "Pref_Token"
        const val PREF_ID = "Pref_Id"
        const val PREF_PHONE_NUMBER = "Pref_Phone_Number"
    }
}