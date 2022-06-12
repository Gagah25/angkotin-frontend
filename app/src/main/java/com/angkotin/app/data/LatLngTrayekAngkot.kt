package com.angkotin.app.data

import com.google.android.gms.maps.model.LatLng

//data class LatLngTrayekAngkot(
//    val trayekAG: TrayekAG
//)
//
//data class TrayekAG(
//    val origin: LatLng = La
//)

data class LatLang(
    val latitude: Double,
    val longitude: Double,
)

class Trayek constructor(origin: LatLng, destination: LatLng) {
    val origin: LatLng = origin
    val destination: LatLng = destination
    val fromOrigin = this.origin.latitude.toString() + "," + this.origin.longitude.toString()
    val toDestination = this.destination.latitude.toString() + "," + this.destination.longitude.toString()
}

class LatLngTrayekAngkot{
    val angkotList: Map<String, Trayek> = mapOf(
        "AG" to Trayek(LatLng(-7.9332112970817095, 112.65815130110964), LatLng(-8.022859232499595, 112.62827301441122)),
        "GL" to Trayek(LatLng(-8.022859232499595, 112.62827301441122), LatLng(-7.924017128933996, 112.59892354827595)),
        "ADL" to Trayek(LatLng(-7.9332112970817095, 112.65815130110964), LatLng(-7.924017128933996, 112.59892354827595)),
        "LDG" to Trayek(LatLng(-7.924017128933996, 112.59892354827595), LatLng(-8.022859232499595, 112.62827301441122)),
        "GM" to Trayek(LatLng(-8.022859232499595, 112.62827301441122), LatLng(-7.9897168757809816, 112.59984218372252))
    )
}