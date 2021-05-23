package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class BencanaResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("jenis_bencana")
	val jenisBencana: String,

	@field:SerializedName("nama_bencana")
	val namaBencana: String,

	@field:SerializedName("kronologi_bencana")
	val kronologiBencana: String,

	@field:SerializedName("longitude")
	val longitude: String
)
