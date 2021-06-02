package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("jenis_pengguna")
    val jenisPengguna: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nomor_wa")
    val nomorWa: String? = null,

    @field:SerializedName("jenis_kelamin")
    val jenisKelamin: String? = null,

    @field:SerializedName("send")
    val send: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("token")
    val token: String? = null,
)
