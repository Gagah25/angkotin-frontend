package com.angkotin.app

import com.angkotin.app.data.*
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

object ApiConfig {
    private const val baseUrl = "https://angkotin-352405.et.r.appspot.com"
    private const val mapsUrl = "https://maps.googleapis.com"
    private const val mlUrl = "http://34.66.224.150:8501"

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

    fun getApiDirectionsService(): ApiDirectionService{
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(mapsUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiDirectionService::class.java)
    }

    fun getApiFindPlacesService(): ApiFindPlacesService{
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(mapsUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiFindPlacesService::class.java)
    }

    fun getApiMlService(): ApiMlService{
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
        return retrofit.create(ApiMlService::class.java)
    }

    fun getApiGeocoding(): ApiGeocodingService{
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(mapsUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiGeocodingService::class.java)
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

interface ApiDirectionService {
    @GET("/maps/api/directions/json")
    fun getDirection(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("units") units: String,
        @Query("mode") mode: String,
        @Query("key") key: String
    ): Call<DirectionsResponse>
}

interface ApiFindPlacesService {
    @GET("/maps/api/place/autocomplete/json")
    fun getFindPlaces(
        @Query("input") input : String,
        @Query("types") types: String,
        @Query("key") key: String
    ): Call<FindPlacesResponse>
}

interface ApiMlService{
    @Headers("Content-Type: application/json")
    @POST("/prediction")
    fun getEstimation(@Body body: DataML): Call<MachineLearnigResponse>
}

interface ApiGeocodingService{
    @GET("/maps/api/geocode/json")
    fun getGeocoding(
        @Query("place_id") place_id: String,
        @Query("key") key: String
    ): Call<GeocodingResponse>
}