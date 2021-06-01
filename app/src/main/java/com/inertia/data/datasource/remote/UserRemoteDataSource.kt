package com.inertia.data.datasource.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.api.UserService
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.LoginResponse
import com.inertia.data.datasource.remote.response.RegisterResponse
import com.inertia.data.repository.user.IUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSource(val service: UserService) {
    fun login(phoneNumber: String): LiveData<ApiResponse<LoginResponse>> {
        val loginLiveData = MutableLiveData<ApiResponse<LoginResponse>>()
        service.login(phoneNumber).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        val userResponse = ApiResponse.success(data)
                        loginLiveData.postValue(userResponse)
                    }
                }else{
                    val userResponse = ApiResponse.error(response.message(), LoginResponse())
                    loginLiveData.postValue(userResponse)
                    Log.e("Login", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val userResponse = ApiResponse.error(t.message, LoginResponse())
                loginLiveData.postValue(userResponse)
                Log.e("Login", "Error: ${t.message}")
            }

        })
        return loginLiveData
    }

    fun register(request: RegisterRequest): LiveData<ApiResponse<RegisterResponse>> {
        val userLiveData = MutableLiveData<ApiResponse<RegisterResponse>>()
        service.register(
            request.nama,
            request.alamat,
            request.jenisKelamin,
            request.phoneNumber,
            request.jenisPengguna
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        userLiveData.postValue(ApiResponse.success(data))
                    }
                }else{
                    userLiveData.postValue(ApiResponse.error(response.message(), RegisterResponse()))
                    Log.e("Register", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                userLiveData.postValue(ApiResponse.error(t.message, RegisterResponse()))
                Log.e("Register", "Error: ${t.message}")
            }

        })
        return userLiveData
    }
}