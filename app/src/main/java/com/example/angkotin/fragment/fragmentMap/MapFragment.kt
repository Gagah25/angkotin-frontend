package com.example.angkotin.fragment.fragmentMap

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.angkotin.R
import com.example.angkotin.data.DataLocation
import com.example.angkotin.data.UserPreference
import com.example.angkotin.databinding.FragmentMapsBinding
import com.example.angkotin.ui.HomeActivity
import com.example.angkotin.ui.SettingActivity
import com.example.angkotin.viewModel.AccountViewModel
import com.example.angkotin.viewModel.DirectionViewModel
import com.example.angkotin.viewModel.MapViewModel
import com.example.angkotin.viewModel.SharedViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*


class MapFragment: Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder: Geocoder
    private lateinit var viewModel: AccountViewModel
    private lateinit var mapViewModel: MapViewModel
    private lateinit var directionViewModel: DirectionViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val data = HashMap<Marker, String>()
    private lateinit var sharedPref: UserPreference
    private lateinit var address: MutableList<Address>
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
    private var driverName: String? = null
    private var driverClicked: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passREsponse = DataLocation()

        fusedLocationClient = FusedLocationProviderClient(requireActivity())
        geocoder = Geocoder(requireActivity(), Locale.getDefault())
        sharedPref = UserPreference(requireActivity())
        token = "Bearer ${sharedPref.fetchToken()}"
        idUser = "${sharedPref.fetchID()}"
        nameUser = "${sharedPref.fetchUserName()}"

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AccountViewModel::class.java)
        mapViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MapViewModel::class.java)
        directionViewModel = ViewModelProvider(this).get(DirectionViewModel::class.java)

        mapViewModel.setDataFirebase()

        val mapFragment = (childFragmentManager.findFragmentById(R.id.map_rute) as SupportMapFragment?)
        mapFragment?.getMapAsync(this)

        binding.apply {
            userAvatar.setImageResource(R.drawable.dummy_pic)

            buttonBack.setOnClickListener { moveToHome() }
            /*POPUP MENU FILTER*/
            buttonFilter.setOnClickListener {
                val popupMenu: PopupMenu = PopupMenu(requireContext(), buttonFilter)
                popupMenu.menuInflater.inflate(R.menu.filter_options,popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.trayek_gl -> buttonFilter.text = "GL"
                        R.id.trayek_ag -> buttonFilter.text = "AG"
                        R.id.trayek_agl -> buttonFilter.text = "AGL"
                        R.id.trayek_ldg -> buttonFilter.text = "LDG"
                        R.id.trayek_gm -> buttonFilter.text = "GM"
                    }
                    true
                })
                popupMenu.show()
            }

            buttonSetting.setOnClickListener { moveToSetting() }
            buttonMyLocation.setOnClickListener { getMyLastLocation() }
            buttonUbah.setOnClickListener {  }
            buttonNaik.setOnClickListener {  }
        }

        binding.apply {
            buttonUbah.setOnClickListener {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        klikAngkotDialog()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val kotaMalang = LatLng(-7.982929, 112.631333)
        val origin = LatLng(-7.9332112970817095, 112.65815130110964)
        val destination = LatLng(-8.022991852006383, 112.62826139250069)

        val fromOrigin = origin.latitude.toString() + "," + origin.longitude.toString()
        val toDestination = destination.latitude.toString() + "," + destination.longitude.toString()

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kotaMalang, 13.5f))

        directionViewModel.setDirectionMap(fromOrigin,toDestination,"driving",origin,destination,mMap)
        //getMyLastLocation()
        mapViewModel.getDataFirebase().observe(viewLifecycleOwner,{

            for (angkot in mapViewModel.getDataFirebase().value!!) {
                val dataLocation = LatLng(
                    angkot.location?.latitude!!,
                    angkot.location?.longitude!!
                )
                if (angkot.role == "driver") {
                    driverName = angkot.name
                    markerDriver = mMap.addMarker(MarkerOptions().position(dataLocation).title(driverName).icon(vectorToBitmap(R.drawable.direction_bus)))
                    markerDriver?.tag = 0

                } else if (angkot.role == "passanger") {
                    markerPassenger = mMap.addMarker(MarkerOptions().position(dataLocation))
                    markerPassenger?.tag = 0
                }
                mMap.setOnMarkerClickListener(this)
            }
        })

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private fun moveToHome(){
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
    }

    private fun moveToSetting(){
        val intent = Intent(requireActivity(), SettingActivity::class.java)
        startActivity(intent)
    }

    private fun getData(data: DataLocation){
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
            requireContext(),
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
                    val data = DataLocation(locationLat, locationLong)
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
                        requireContext(),
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
        sharedViewModel.setNameDriver(marker.title.toString())
        view?.findNavController()?.navigate(R.id.action_mapFragment_to_profilDriverFragment)
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
        }
        return false
    }

    private fun klikAngkotDialog() {
        dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(getString(R.string.teks_warning_klik_angkot))
        dialogBuilder.setPositiveButton("Oke", DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
        })
        dialog = dialogBuilder.create()
        dialog.show()
    }

    companion object{
        const val EXTRA_NAME = "Extra_Name"
    }
}