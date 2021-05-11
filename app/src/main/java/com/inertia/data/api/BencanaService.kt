package com.inertia.data.api

import com.inertia.data.response.BencanaResponse
import retrofit2.Call
import retrofit2.http.GET

interface BencanaService {
    @GET("bencana")
    fun getAllBencana(): Call<List<BencanaResponse>>
}