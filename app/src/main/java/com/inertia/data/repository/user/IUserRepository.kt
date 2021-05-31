package com.inertia.data.repository.user

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.request.RegisterRequest
import com.inertia.data.datasource.remote.response.RegisterResponse

interface IUserRepository {
    fun getUser(): UserEntity

    fun login(phoneNumber: String, callback: LoginCallback)

    fun register(request: RegisterRequest): LiveData<UserEntity>

    fun setUser(userEntity: UserEntity?)

    interface LoginCallback {
        fun onLoginSuccessCallback(userEntity: UserEntity, verificationCode: String?)
    }
}