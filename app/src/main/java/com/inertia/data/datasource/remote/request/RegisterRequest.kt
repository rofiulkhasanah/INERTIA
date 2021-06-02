package com.inertia.data.datasource.remote.request

data class RegisterRequest(
    val nama: String,
    val alamat: String,
    val jenisKelamin: String,
    val phoneNumber: String,
    val jenisPengguna: String,
)