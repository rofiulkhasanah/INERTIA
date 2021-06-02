package com.inertia.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @field:SerializedName("country")
    val country: String,

    @field:SerializedName("country_code")
    val countryCode: String,

    @field:SerializedName("city")
    val city: String,

    @field:SerializedName("road")
    val road: String,

    @field:SerializedName("postcode")
    val postcode: String,

    @field:SerializedName("weather")
    val weather: Weather,

    @field:SerializedName("state")
    val state: String,

    @field:SerializedName("village")
    val village: String,
)

data class Weather(

    @field:SerializedName("cloud")
    val cloud: String,

    @field:SerializedName("temp")
    val temp: Double,

    @field:SerializedName("humidity")
    val humidity: Int,

    @field:SerializedName("wind_speed")
    val windSpeed: Double,
)
