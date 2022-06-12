package com.angkotin.app.data

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class BoundsGeo(

	@field:SerializedName("southwest")
	val southwest: SouthwestGeo? = null,

	@field:SerializedName("northeast")
	val northeast: NortheastGeo? = null
)

data class SouthwestGeo(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class NortheastGeo(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class Viewport(

	@field:SerializedName("southwest")
	val southwest: SouthwestGeo? = null,

	@field:SerializedName("northeast")
	val northeast: NortheastGeo? = null
)

data class ResultsItem(

	@field:SerializedName("formatted_address")
	val formattedAddress: String? = null,

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("address_components")
	val addressComponents: List<AddressComponentsItem?>? = null,

	@field:SerializedName("place_id")
	val placeId: String? = null
)

data class Geometry(

	@field:SerializedName("viewport")
	val viewport: Viewport? = null,

	@field:SerializedName("bounds")
	val bounds: BoundsGeo? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("location_type")
	val locationType: String? = null
)

data class Location(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class AddressComponentsItem(

	@field:SerializedName("types")
	val types: List<String?>? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("long_name")
	val longName: String? = null
)
