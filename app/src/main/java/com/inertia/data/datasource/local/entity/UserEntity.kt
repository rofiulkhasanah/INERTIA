package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    var name: String? = null,
    var phoneNumber: String? = null,
    var provinsi: String? = null,
    var kota: String? = null
): Parcelable