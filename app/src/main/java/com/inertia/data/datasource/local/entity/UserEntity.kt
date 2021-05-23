package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val nama: String? = null,
    val jenisPengguna: String? = null,
    val id: String? = null,
    val nomorWa: String? = null,
    val jenisKelamin: String? = null,
    val send: String? = null,
    val alamat: String? = null,
): Parcelable