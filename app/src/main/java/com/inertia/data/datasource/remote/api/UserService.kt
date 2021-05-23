package com.inertia.data.datasource.remote.api

import com.inertia.data.datasource.remote.response.UserLoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("account/get/{phone_number}")
    fun login(@Path("phone_number") phoneNumber: String): Call<UserLoginResponse>
}