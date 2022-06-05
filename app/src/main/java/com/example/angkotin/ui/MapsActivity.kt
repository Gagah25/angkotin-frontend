package com.example.angkotin.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.angkotin.R
import com.example.angkotin.data.DataLocation
import com.example.angkotin.data.UserPreference
import com.example.angkotin.databinding.MapsLokasiBinding
import com.example.angkotin.fragment.fragmentSearch.SearchPageFragment
import com.example.angkotin.viewModel.AccountViewModel
import com.example.angkotin.viewModel.MapViewModel
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import java.util.*
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
    private var locationLong: Double = 0.0
    private var locationLat: Double = 0.0
    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private var markerDriver: Marker? = null
    private var markerPassenger: Marker? = null

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
            userAvatar.setImageResource(R.drawable.dummy_pic)
            buttonBack.setOnClickListener { moveToHome() }
            buttonFilter.setOnClickListener { popUpMenu() }
            buttonSetting.setOnClickListener { moveToSetting() }
            buttonMyLocation.setOnClickListener { getMyLastLocation() }
            //buttonUbah.setOnClickListener { moveToSearchPage() }
            buttonNaik.setOnClickListener { klikAngkotDialog() }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val kotaMalang = LatLng(-7.982929, 112.631333)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kotaMalang, 13.5f))

        //getMyLastLocation()
        mapViewModel.getDataFirebase().observe(this,{
            Log.d("MapViewModel", mapViewModel.getDataFirebase().value.toString())
            for (angkot in mapViewModel.getDataFirebase().value!!) {
                val dataLocation = LatLng(
                    angkot.location?.latitude!!,
                    angkot.location?.longitude!!
                )
                if (angkot.role == "driver") {
                    markerDriver = mMap.addMarker(MarkerOptions().position(dataLocation).title(angkot.name).icon(vectorToBitmap(R.drawable.direction_bus)))
                    markerDriver?.tag = 0
                } else if (angkot.role == "passanger") {
                    markerPassenger = mMap.addMarker(MarkerOptions().position(dataLocation))
                    markerPassenger?.tag = 0
                }
//                mMap.setOnMarkerClickListener(this)
            }
        })

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun moveToHome(){
        val intent = Intent(this@MapsActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun popUpMenu() {
        val popupMenu: PopupMenu = PopupMenu(this@MapsActivity, binding.buttonFilter)
        popupMenu.menuInflater.inflate(R.menu.filter_options,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.trayek_gl -> binding.buttonFilter.text = "GL"
                R.id.trayek_ag -> binding.buttonFilter.text = "AG"
                R.id.trayek_agl -> binding.buttonFilter.text = "AGL"
                R.id.trayek_ldg -> binding.buttonFilter.text = "LDG"
                R.id.trayek_gm -> binding.buttonFilter.text = "GM"
            }
            true
        })
        popupMenu.show()
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startLocation, 17f))
    }

    private fun vectorToBitmap(@DrawableRes id: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val clickCount = marker.tag as? Int
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        return false
    }

    private fun moveToSearchPage(){
//        val mMoveToSearchPage = SearchPageFragment()
//        val mFragmentManager = supportFragmentManager
//        mFragmentManager.commit {
//            .replace(R.id.search_page, mMoveToSearchPage, SearchPageFragment::class.java)
//        }
    }

    private fun klikAngkotDialog() {
        dialogBuilder = AlertDialog.Builder(this@MapsActivity)
        dialogBuilder.setMessage(getString(R.string.teks_warning_klik_angkot))
        dialogBuilder.setPositiveButton("Oke", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        })
        dialog = dialogBuilder.create()
        dialog.show()
    }
}


