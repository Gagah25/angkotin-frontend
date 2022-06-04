package com.example.angkotin.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.angkotin.R
import com.example.angkotin.data.DataLocation
import com.example.angkotin.data.PassengerResponse
import com.example.angkotin.data.UserPreference
import com.example.angkotin.databinding.MapsLokasiBinding
import com.example.angkotin.firebase.FirebaseUtils
import com.example.angkotin.viewModel.AccountViewModel
import com.example.angkotin.viewModel.MapViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class MapsActivity: AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,
    GoogleMap.OnMarkerClickListener {
    private lateinit var binding: MapsLokasiBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder
    private lateinit var viewModel: AccountViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var sharedPref: UserPreference
    private lateinit var address: MutableList<android.location.Address>
    private lateinit var token: String
    private lateinit var idUser: String
    private lateinit var nameUser: String
    private lateinit var passREsponse: DataLocation
    private var allLatLng = ArrayList<LatLng>()
    private var locationLong: Double = 0.0
    private var locationLat: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapsLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        passREsponse = DataLocation()

        fusedLocationClient = FusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())
        sharedPref = UserPreference(this)
        token = "Bearer ${sharedPref.fetchToken()}"
        idUser = "${sharedPref.fetchID()}"
        nameUser = "${sharedPref.fetchUserName()}"

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AccountViewModel::class.java)
        mapViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MapViewModel::class.java)

        mapViewModel.setDataFirebase()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_rute) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.apply {
            navigationFilter.setNavigationItemSelectedListener(this@MapsActivity)
            userAvatar.setImageResource(R.drawable.dummy_pic)

            buttonBack.setOnClickListener { moveToHome() }
            buttonFilter.setOnClickListener{
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
                drawerLayout.openDrawer(GravityCompat.END)
            }
            buttonSetting.setOnClickListener { moveToSetting() }
            buttonMyLocation.setOnClickListener { getMyLastLocation() }
            buttonUbah.setOnClickListener {  }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val kotaMalang = LatLng(-7.982929, 112.631333)

        mMap.setOnMarkerClickListener(this)
        mMap.addMarker(MarkerOptions().position(kotaMalang).title("angkot"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kotaMalang, 13.5f))

        //getMyLastLocation()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun moveToHome(){
        val intent = Intent(this@MapsActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToSetting(){
        val intent = Intent(this@MapsActivity, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    private fun getData(data: com.example.angkotin.data.DataLocation){
        viewModel.setDataLocation(token, idUser, data)

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }
                else -> {
                    // No location access granted.
                }
            }
        }
    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    locationLat = location.latitude
                    locationLong = location.longitude
                    val data = com.example.angkotin.data.DataLocation(locationLat, locationLong)
                    Log.d("Lat", locationLat.toString())
                    Log.d("Long", locationLong.toString())

                    address = geocoder.getFromLocation(locationLat, locationLong,1)

                    val alamat: String = address.get(0).getAddressLine(0)

                    binding.apply {
                        street.text = alamat
                        userDetail.text = "${nameUser}, ${alamat}"
                    }

                    getData(data)
                    showStartMarker(location)
                } else {
                    Toast.makeText(
                        this@MapsActivity,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun showStartMarker(location: Location) {
        val startLocation = LatLng(location.latitude, location.longitude)
        mMap.addMarker(
            MarkerOptions()
                .position(startLocation)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 17f))
    }

    companion object {
        private const val TAG = "MapsActivity"
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }
}