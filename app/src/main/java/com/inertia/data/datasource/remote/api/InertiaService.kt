package com.inertia.data.datasource.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InertiaService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://3699259e-1f22-40cf-b61a-f1afb23e7150.mock.pstmn.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBencanaService(): BencanaService = retrofit.create(BencanaService::class.java)

    fun getUserService(): UserService = retrofit.create(UserService::class.java)

    fun getCuacaService(): CuacaService = retrofit.create(CuacaService::class.java)
}