package com.inertia.data.datasource.remote.api

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InertiaService {

    val hostname =  "api.themoviedb.org"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        .build()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(120, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://35.224.208.225/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    private val retrofitPenilaian = Retrofit.Builder()
        .baseUrl("http://34.70.161.86/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getBencanaService(): BencanaService = retrofit.create(BencanaService::class.java)

    fun getUserService(): UserService = retrofit.create(UserService::class.java)

    fun getCuacaService(): CuacaService = retrofit.create(CuacaService::class.java)

    fun getPenilaian(): PenilaianService = retrofitPenilaian.create(PenilaianService::class.java)

    fun getTerdampak(): TerdampakService = retrofitPenilaian.create(TerdampakService::class.java)
}