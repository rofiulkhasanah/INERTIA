package com.inertia.data.datasource.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CuacaEntity(
    val temp: Double,
    val cloud: String,
    val wind: Double,
    val humidity: Int,
) : Parcelable
