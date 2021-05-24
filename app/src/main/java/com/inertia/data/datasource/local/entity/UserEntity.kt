package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    var nama: String? = null,
    val jenisPengguna: String? = null,
    var nomorWa: String? = null,
    var jenisKelamin: String? = null,
    var alamat: String? = null
): Parcelable