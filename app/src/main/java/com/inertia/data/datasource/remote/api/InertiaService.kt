package com.inertia.data.datasource.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InertiaService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://6099de5e0f5a130017219b40.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBencanaService() = retrofit.create(BencanaService::class.java)

    fun getUserService() = retrofit.create(UserService::class.java)
}