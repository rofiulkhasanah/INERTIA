package com.inertia.data.repository.user

import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.request.RegisterRequest

interface IUserRepository {
    fun getUser(): UserEntity

    fun login(phoneNumber: String, callback: LoginCallback)

    fun register(request: RegisterRequest, callback: RegisterCallback)

    fun setUser(userEntity: UserEntity?)

    interface LoginCallback {
        fun onLoginSuccessCallback(userEntity: UserEntity, verificationCode: String?)
    }

    interface RegisterCallback {
        fun onRegisterSuccessCallback(userEntity: UserEntity, verificationCode: String?)
    }
}