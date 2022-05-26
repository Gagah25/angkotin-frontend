package com.example.angkotin

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("success")
    val error: Boolean? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)
