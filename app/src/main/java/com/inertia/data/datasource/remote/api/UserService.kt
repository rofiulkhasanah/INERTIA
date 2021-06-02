package com.inertia.data.datasource.remote.api

import com.inertia.data.datasource.remote.response.LoginResponse
import com.inertia.data.datasource.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("account/get/{phone_number}")
    fun login(@Path("phone_number") phoneNumber: String): Call<LoginResponse>

    @POST("account/add")
    fun register(
        @Query("nama") nama: String,
        @Query("alamat") alamat: String,
        @Query("jenis_kelamin") jenisKelamin: String,
        @Query("nomor_wa") phoneNumber: String,
        @Query("jenis_pengguna") jenisPengguna: String,
    ): Call<RegisterResponse>
}