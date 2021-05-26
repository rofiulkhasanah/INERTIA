package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("jenis_pengguna")
	val jenisPengguna: String,

	@field:SerializedName("nomor_wa")
	val nomorWa: String,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String,

	@field:SerializedName("send")
	val send: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("id_pengguna")
	val idPengguna: String,

	@field:SerializedName("token")
	val token: String
)
