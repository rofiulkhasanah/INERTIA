package com.inertia.data.datasource.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InertiaService {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://3699259e-1f22-40cf-b61a-f1afb23e7150.mock.pstmn.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getBencanaService(): BencanaService = retrofit.create(BencanaService::class.java)

    fun getUserService(): UserService = retrofit.create(UserService::class.java)

    fun getCuacaService(): CuacaService = retrofit.create(CuacaService::class.java)
}