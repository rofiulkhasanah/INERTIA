package com.inertia.data.datasource.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InertiaService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://b9d8f4d4-6968-4ad4-acc4-f744ed82686b.mock.pstmn.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBencanaService(): BencanaService = retrofit.create(BencanaService::class.java)

    fun getUserService(): UserService = retrofit.create(UserService::class.java)
}