package com.inertia.data.datasource.remote.api

import com.inertia.data.datasource.remote.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CuacaService {
    @GET("weather/{latitude}/{longitude}")
    fun getCuaca(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
    ): Call<WeatherResponse>
}