package com.inertia.ui.profile

import androidx.lifecycle.ViewModel
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.repository.user.UserRepository

class ProfileViewModel(val repository: UserRepository): ViewModel() {
    fun getUser() = repository.getUser()

    fun logout() {
        val userEntity = UserEntity()
        repository.setUser(userEntity)
    }
}