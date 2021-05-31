package com.inertia.data.repository.user

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.datasource.remote.response.ApiResponse
import com.inertia.data.datasource.remote.response.LoginResponse
import com.inertia.data.datasource.remote.response.RegisterResponse

interface IUserRepository {
    fun getUser(): UserEntity

    fun login(phoneNumber: String): LiveData<ApiResponse<LoginResponse>>

    fun register(request: RegisterRequest): LiveData<ApiResponse<UserEntity>>

    fun setUser(userEntity: UserEntity?)
}