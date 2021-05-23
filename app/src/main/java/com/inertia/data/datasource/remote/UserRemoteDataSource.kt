package com.inertia.data.datasource.remote

import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.api.UserService
import com.inertia.data.datasource.remote.response.UserLoginResponse

class UserRemoteDataSource(val service: UserService) {
    fun login(phoneNumber: String): UserLoginResponse {
        return UserLoginResponse("Budi Jayanto", phoneNumber,
            "Jawa Timur", "Malang", "123456")
    }

    fun register(entity: UserEntity) {

    }
}