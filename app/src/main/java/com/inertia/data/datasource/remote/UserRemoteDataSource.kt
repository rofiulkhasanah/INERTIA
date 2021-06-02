package com.inertia.data.datasource.remote

import android.util.Log
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.api.UserService
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.datasource.remote.response.LoginResponse
import com.inertia.data.datasource.remote.response.RegisterResponse
import com.inertia.data.repository.user.IUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSource(val service: UserService) {
    fun login(phoneNumber: String, callback: IUserRepository.LoginCallback) {
        service.login(phoneNumber).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val user = UserEntity(data.nama, data.jenisPengguna, data.nomorWa,
                            data.jenisKelamin, data.alamat)
                        val verificationCode = data.token
                        callback.onLoginSuccessCallback(user, verificationCode)
                    }
                } else {
                    Log.e("Login", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Login", "Error: ${t.message}")
            }

        })
    }

    fun register(request: RegisterRequest, callback: IUserRepository.RegisterCallback) {
        service.register(
            request.nama,
            request.alamat,
            request.jenisKelamin,
            request.phoneNumber,
            request.jenisPengguna
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val user = UserEntity(data.nama, data.jenisPengguna, data.nomorWa,
                            data.jenisKelamin, data.alamat)
                        val verificationCode = data.token
                        callback.onRegisterSuccessCallback(user, verificationCode)
                    }
                } else {
                    Log.e("Register", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Register", "Error: ${t.message}")
            }

        })
    }
}