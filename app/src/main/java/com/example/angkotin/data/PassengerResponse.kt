package com.example.angkotin.data

import com.google.gson.annotations.SerializedName

data class PassengerResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Location(

	@field:SerializedName("latitude")
	val latitude: Int? = null,

	@field:SerializedName("longitude")
	val longitude: Int? = null
)

data class CreatedAt(

	@field:SerializedName("seconds")
	val seconds: Int? = null,

	@field:SerializedName("nanoseconds")
	val nanoseconds: Int? = null
)
