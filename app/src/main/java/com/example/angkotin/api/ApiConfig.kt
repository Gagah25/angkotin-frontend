package com.example.angkotin

import com.example.angkotin.data.*
import com.example.angkotin.data.UserPreference
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

object ApiConfig {
    private const val baseUrl = "https://angkotin-backend.herokuapp.com"

    fun getApiService(): ApiInterface {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

}

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("/register/passengers")
    fun getStringScalar(@Body body: RegisterResponse): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("/auth")
    fun postLogin(@Body body: LoginResponse): Call<LoginResponse>

    @GET("/passengers/{id}")
    fun getPassenger(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<PassengerResponse>

    @PUT("/passengers/{id}")
    fun updateLocationUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body params: DataLocation
    ): Call<PassengerResponse>
}