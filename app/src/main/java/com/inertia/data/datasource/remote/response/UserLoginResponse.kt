package com.inertia.data.datasource.remote.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLoginResponse(
    var name: String? = null,
    var phoneNumber: String? = null,
    var provinsi: String? = null,
    var kota: String? = null
): Parcelable