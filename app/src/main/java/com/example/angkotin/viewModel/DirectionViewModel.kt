package com.example.angkotin.viewModel

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.angkotin.ApiConfig
import com.example.angkotin.BuildConfig.MAPS_API_KEY
import com.example.angkotin.R
import com.example.angkotin.data.DirectionsResponse
import com.example.angkotin.data.LegsItem
import com.example.angkotin.data.RoutesItem
import com.example.angkotin.data.StepsItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.SphericalUtil
import retrofit2.Call
import retrofit2.Response


class DirectionViewModel(application: Application) : AndroidViewModel(application) {
    var points = ArrayList<LatLng>()
    var polyLineOptions: PolylineOptions? = null
    val _dataDistance = MutableLiveData<Double>()
    private val context = getApplication<Application>().applicationContext

    fun setDirectionMap(fromOrigin: String, toDestination: String, mode: String, origin: LatLng, destination: LatLng, mMap: GoogleMap){

        val client = ApiConfig.getApiDirectionsService().getDirection(fromOrigin, toDestination,"metric", mode, "${MAPS_API_KEY}")
         client.enqueue(object : retrofit2.Callback<DirectionsResponse>{
            override fun onResponse(
                call: Call<DirectionsResponse>,
                response: Response<DirectionsResponse>,
            ) {
                if (response.isSuccessful){
                    val status = response.body()?.status
                    if (status.equals("OK")){
                        val routes: List<RoutesItem?>? = response.body()?.routes

                        for (i in routes?.indices!!){
                            points = ArrayList()
                            polyLineOptions = PolylineOptions()
                            var legs: List<LegsItem?>? = routes.get(i)?.legs

                            for (j in legs?.indices!!){
                                var steps: List<StepsItem?>? = legs.get(j)?.steps
                                var distanceAPI = legs[j]?.distance?.text?.take(4)?.toDouble()
                                Log.d("Jarak", distanceAPI.toString())

                                for (k in steps?.indices!!){
                                    var polyLine: String? = steps.get(k)?.polyline?.points
                                    var list: List<LatLng>? = PolyUtil.decode(polyLine)

                                    for (l in 0 until list?.size!!) {
                                        val position = LatLng(list[l].latitude, list[l].longitude)
                                        points.add(position)
                                    }
                                }
                            }
                        }
                        polyLineOptions?.addAll(points)
                        polyLineOptions?.width(10f)
                        polyLineOptions?.color(ContextCompat.getColor(context , R.color.blue_400))
                    }
                    mMap?.addPolyline(polyLineOptions!!)
                    mMap?.addMarker(MarkerOptions().position(origin).title("Marker 1"))
                    mMap?.addMarker(MarkerOptions().position(destination).title("Marker 2"))

                    val bounds = LatLngBounds.Builder()
                        .include(origin)
                        .include(destination).build()
                    val point = Point()
                    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    windowManager?.defaultDisplay?.getSize(point)
                    mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, point.x, 150, 30))
                }
            }

            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getDistance():LiveData<Double>{
        return _dataDistance
    }
}
