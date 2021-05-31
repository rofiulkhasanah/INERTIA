package com.inertia.data.repository.user

import androidx.lifecycle.LiveData
import com.inertia.data.datasource.local.UserLocalDataSource
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.datasource.remote.UserRemoteDataSource
import com.inertia.data.datasource.remote.request.RegisterRequest

class UserRepository(
    val local: UserLocalDataSource,
    val remote: UserRemoteDataSource
) : IUserRepository {
    override fun getUser(): UserEntity = local.getUser()

    override fun login(phoneNumber: String, callback: IUserRepository.LoginCallback) =
        remote.login(phoneNumber, callback)

    override fun register(request: RegisterRequest): LiveData<UserEntity> = remote.register(request)

    override fun setUser(userEntity: UserEntity?) {
        local.setUser(userEntity)
    }
}