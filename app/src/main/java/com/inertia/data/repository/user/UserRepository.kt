package com.inertia.data.repository.user

import com.inertia.data.datasource.local.UserLocalDataSource
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.UserRemoteDataSource
import com.inertia.data.datasource.remote.request.RegisterRequest

class UserRepository(
    val local: UserLocalDataSource,
    val remote: UserRemoteDataSource
) : IUserRepository {
    override fun getUser(): UserEntity = local.getUser()

    override fun login(phoneNumber: String) = remote.login(phoneNumber)

    override fun register(request: RegisterRequest) = remote.register(request)

    override fun setUser(userEntity: UserEntity?) {
        local.setUser(userEntity)
    }
}