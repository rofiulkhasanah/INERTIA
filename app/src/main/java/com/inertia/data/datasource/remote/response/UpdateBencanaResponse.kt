package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateBencanaResponse(

	@field:SerializedName("id_aduan")
	val idAduan: String,

	@field:SerializedName("kronologi")
	val kronologi: String? = null,

	@field:SerializedName("lat_long")
	val latLong: String? = null,

	@field:SerializedName("sender_wa_number")
	val senderWaNumber: String? = null,

	@field:SerializedName("gambar_uri")
	val gambarUri: String? = null,

	@field:SerializedName("jenis_bencana")
	val jenisBencana: String? = null,

	@field:SerializedName("waktu_bencana")
	val waktuBencana: String? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
