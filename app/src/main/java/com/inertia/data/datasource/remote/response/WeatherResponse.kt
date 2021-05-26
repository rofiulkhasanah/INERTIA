package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @field:SerializedName("temp")
    val temp: Double,

    @field:SerializedName("cloud")
    val cloud: Int,

    @field:SerializedName("wind")
    val wind: Double,

    @field:SerializedName("humidity")
    val humidity: Int
)