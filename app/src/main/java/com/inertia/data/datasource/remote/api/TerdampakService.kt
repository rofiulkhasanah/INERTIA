package com.inertia.data.datasource.remote.api

import com.inertia.data.datasource.remote.response.TerdampakResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TerdampakService {
    @GET("/getkasus")
    fun getAllTerdampak(@Query("nomorWa") nomorWa: String): Call<TerdampakResponse>
}