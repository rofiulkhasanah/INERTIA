package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    var nama: String? = null,
    val jenisPengguna: String? = null,
    var nomorWa: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null
): Parcelable