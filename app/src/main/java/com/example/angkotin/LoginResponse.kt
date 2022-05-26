package com.example.angkotin

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("success")
    val error: Boolean? = null,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)
