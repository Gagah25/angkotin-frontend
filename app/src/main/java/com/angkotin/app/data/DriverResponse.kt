package com.angkotin.app.data

import com.google.gson.annotations.SerializedName

data class DriverResponse(
    @field:SerializedName("driverMeta")
    var driverMeta: DriverData? = null,

    @field:SerializedName("location")
    var location: DriverLocation? = null ,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("nik")
    var nik: String? = null,

    @field:SerializedName("phoneNumber")
    var phoneNumber: String? = null,

    @field:SerializedName("role")
    var role: String? = null,
)

data class DriverData(
    @field:SerializedName("createdAt")
    val angkotNumber: String? = null,

    @field:SerializedName("isActive")
    val isActive: Boolean? = null
)

data class DriverLocation(
    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null,
)