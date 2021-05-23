package com.inertia.data.repository.user

import com.inertia.data.datasource.local.UserLocalDataSource
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.UserRemoteDataSource

class UserRepository(
    val local: UserLocalDataSource,
    val remote: UserRemoteDataSource
) : IUserRepository {
    override fun getUser(): UserEntity = local.getUser()

    override fun login(phoneNumber: String, callback: IUserRepository.LoginCallback) {
        val response = remote.login(phoneNumber)

        val user = UserEntity()
        user.name = response.name
        user.phoneNumber = response.phoneNumber
        user.kota = response.kota
        user.provinsi = response.provinsi

        callback.onLoginSuccessCallback(user, response.verification_code)

//        userService.login(phoneNumber).enqueue(object : Callback<UserLoginResponse> {
//            override fun onResponse(
//                call: Call<UserLoginResponse>,
//                response: Response<UserLoginResponse>
//            ) {
//                val data = response.body()
//
//                val user = UserEntity()
//                user.name = data?.name
//                user.phoneNumber = data?.phoneNumber
//                user.kota = data?.kota
//                user.provinsi = data?.provinsi
//
//                callback.onLoginSuccessCallback(user, data?.verification_code)
//            }
//
//            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
//                Log.e("Login", "onFailure: ${t.message}")
//            }
//
//        })
    }

    override fun setUser(userEntity: UserEntity?) {
        local.setUser(userEntity)
    }
}