package com.inertia.data.datasource.remote.response

data class WeatherResponse(
    val temp: Double,
    val cloud: Int,
    val wind: Double,
    val humidity: Int
)