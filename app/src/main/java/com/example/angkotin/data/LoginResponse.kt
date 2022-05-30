package com.example.angkotin.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("password")
	val password: String
)

data class DataLogin(

	@field:SerializedName("endPoint")
	val endPoint: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
