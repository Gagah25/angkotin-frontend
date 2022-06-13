package com.angkotin.app.data

import com.google.gson.annotations.SerializedName

data class MachineLearnigResponse(
    @field:SerializedName("success")
    var success: Boolean? = null,

    @field:SerializedName("data")
    var data: dataRequest
)


data class dataRequest(
    @field:SerializedName("predictions")
    var predictions: List<List<Double>>
)

data class DataML(

    @field:SerializedName("jalur")
    var jalur: String? = null,

    @field:SerializedName("awal")
    var awal: Int? = null,

    @field:SerializedName("akhir")
    var akhir: Int? = null,

    @field:SerializedName("kecepatan")
    var kecepatan: Int? = null,

    @field:SerializedName("latitude")
    var latitude: Double? = null,

    @field:SerializedName("longitude")
    var longitude: Double? = null
)
