package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class TerdampakResponse(

	@field:SerializedName("idKasus")
	val idKasus: String,

	@field:SerializedName("nomorWa")
	val nomorWa: String,

	@field:SerializedName("idBencana")
	val idBencana: String,

	@field:SerializedName("idSub")
	val idSub: String,

	@field:SerializedName("kota")
	val kota: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("provinsi")
	val provinsi: String,

	@field:SerializedName("tanggal")
	val tanggal: String,

	@field:SerializedName("namaBencana")
	val namaBencana: String
)
