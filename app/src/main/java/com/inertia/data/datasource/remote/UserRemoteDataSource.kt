package com.inertia.data.datasource.remote

import com.inertia.data.datasource.remote.api.UserService
import com.inertia.data.datasource.remote.response.UserLoginResponse

class UserRemoteDataSource(service: UserService) {
    fun getUser(phoneNumber: String): UserLoginResponse {
        return UserLoginResponse("Budi Jayanto", phoneNumber,
            "Jawa Timur", "Malang", "123456")
    }
}