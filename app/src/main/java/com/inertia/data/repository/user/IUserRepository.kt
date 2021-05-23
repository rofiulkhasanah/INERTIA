package com.inertia.data.repository.user

import com.inertia.data.datasource.local.entity.UserEntity

interface IUserRepository {
    fun getUser(): UserEntity

    fun login(phoneNumber: String, callback: LoginCallback)

    fun setUser(userEntity: UserEntity?)

    interface LoginCallback {
        fun onLoginSuccessCallback(userEntity: UserEntity, verificationCode: String?)
    }
}